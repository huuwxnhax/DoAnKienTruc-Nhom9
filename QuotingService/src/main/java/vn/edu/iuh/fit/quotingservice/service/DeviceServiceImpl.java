package vn.edu.iuh.fit.quotingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.quotingservice.dto.CustomerDTO;
import vn.edu.iuh.fit.quotingservice.entity.Device;
import vn.edu.iuh.fit.quotingservice.repositories.DeviceRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository deviceRepository;
    public Map<Long, CustomerDTO> customerDTOMap = new HashMap<>();
    private final RestTemplate restTemplate;
    private static final String CUSTOMER_SERVICE_URL = "http://localhost:8083/api/customers/";

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, RestTemplate restTemplate) {
        this.deviceRepository = deviceRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Device createDeviceQuote(Device device) {
        Device saveDevice = deviceRepository.save(device);
        return saveDevice;
    }

    private Long retrieveCustomerId(Long customerId) {
        String url = CUSTOMER_SERVICE_URL + customerId;
        try {
            CustomerDTO customer = restTemplate.getForObject(url, CustomerDTO.class);
            return customer != null ? customer.getId() : null;
        } catch (Exception e) {
            // Xử lý khi gặp lỗi trong quá trình gửi yêu cầu hoặc nhận phản hồi từ service CustomerService
            return null;
        }
    }

    @Override
    public void deleteDeviceQuote(Long id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public Device getDeviceQuoteById(Long id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        return deviceOptional.orElse(null);
    }
}
