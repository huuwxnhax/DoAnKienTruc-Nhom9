package vn.edu.iuh.fit.quotingservice.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.quotingservice.entity.Device;
import vn.edu.iuh.fit.quotingservice.entity.Quote;
import vn.edu.iuh.fit.quotingservice.repositories.DeviceRepository;
import vn.edu.iuh.fit.quotingservice.repositories.QuotingRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuoteServiceImpl implements QuoteService{

    @Autowired
    private final QuotingRepository quotingRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    private final Map<String, Double> modelBasePriceMap = new HashMap<>();
    private final Map<String, Double> modelManufacPriceMap = new HashMap<>();
    private final Map<String, Double> materialPriceMap = new HashMap<>();

    public QuoteServiceImpl(QuotingRepository quotingRepository, DeviceRepository deviceRepository) {
        this.quotingRepository = quotingRepository;
        loadPricingRules();
    }

    @Override
    public Quote getQuote(Long id) {
        return quotingRepository.findById(id).orElse(null);
    }

    @Override
    public void createQuote(Device device) {
        double proposedPrice = estimatePriceForDevice(device);

        Quote quote = new Quote(proposedPrice,device);

        // Lưu báo giá vào cơ sở dữ liệu
        quotingRepository.save(quote);
    }

    private void loadPricingRules() {
        try (CSVReader csvReader = new CSVReader(new FileReader(new ClassPathResource("pricing_rules.csv").getFile()))) {
            String[] line;
            boolean isHeader = true; // Biến để bỏ qua dòng tiêu đề
            while ((line = csvReader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Bỏ qua dòng tiêu đề
                }

                String type = line[0].trim();
                String model = line[1].trim();
                String material = line[2].trim();
                String basePriceStr = line[3].trim();
                String manufacPricestr = line[4].trim();

                try {
                    if (!basePriceStr.isEmpty()) {
                        double basePrice = Double.parseDouble(basePriceStr);
                        modelBasePriceMap.put(model, basePrice);
                    }
                    if (!manufacPricestr.isEmpty()) {
                        double manufacPrice = Double.parseDouble(manufacPricestr);
                        if (!model.isEmpty()) {
                            modelManufacPriceMap.put(model, manufacPrice);
                        } else if (!material.isEmpty()) {
                            materialPriceMap.put(material, manufacPrice);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing price for line: " + String.join(",", line));
                    e.printStackTrace();
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }


    private double estimatePriceForDevice(Device device) {
        double basePrice = 0.0;
        double priceNhom = 2.0;
        double priceDong = 6.0;
        double priceSat = 4.0;
        double priceOther = 1.0;


        String type = device.getType();
        String material = device.getMaterial();
        double weight = device.getWeight();
        String model = device.getModel();
        int usageDays = device.getUsageDay();
        double condition = device.getCondition();

        if (modelBasePriceMap.containsKey(model)) {
            if(condition < 50.0) {
                basePrice += modelBasePriceMap.get(model) * 0.2;
            } else if (condition < 70.0) {
                basePrice += modelBasePriceMap.get(model) * 0.4;
            } else if (condition < 80.0) {
                basePrice += modelBasePriceMap.get(model) * 0.5;
            } else if (condition < 90) {
                basePrice += modelBasePriceMap.get(model) * 0.6;
            } else {
                basePrice += modelBasePriceMap.get(model);
            }
        }


//        // Chỉ tính giá dựa trên trọng lượng nếu loại là đồ gia dụng
        if (type.equals("Tu lanh") || type.equals("May giat") || type.equals("May lanh") || type.equals("Tivi")) {
//            if (materialPriceMap.containsKey(material)) {
//                double materialPrice = materialPriceMap.get(material);
//                basePrice += materialPriceMap.get(material);
//            }
            if(material.equals("Nhom")) {
                double weightPrice = weight * priceNhom;
                basePrice += weightPrice;
            } else if (material.equals("Sat")) {
                double weightPrice = weight * priceSat;
                basePrice += weightPrice;
            } else if (material.equals("Dong")) {
                double weightPrice = weight * priceDong;
                basePrice += weightPrice;
            } else {
                double weightPrice = weight * priceOther;
                basePrice += weightPrice;
            }
        }

        // Tính giá dựa trên thời gian sử dụng
        if (usageDays <= 30) {
            basePrice += modelManufacPriceMap.get(model) * 0.4;
        } else if (usageDays <= 60) {
            basePrice += modelManufacPriceMap.get(model) * 0.3;
        } else if (usageDays <= 120) {
            basePrice += modelManufacPriceMap.get(model) * 0.2;
        } else {
            basePrice += modelManufacPriceMap.get(model) * 0.1;
        }

        return basePrice;
    }


    @Override
    public List<Quote> getAllQuotes() {
        return quotingRepository.findAll();
    }
}
