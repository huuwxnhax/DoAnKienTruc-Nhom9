package vn.edu.iuh.fit.quotingservice.service;

import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.quotingservice.entity.Device;

@Service
public interface DeviceService {
    public Device createDeviceQuote(Device device);
    public void deleteDeviceQuote(Long id);
    public Device getDeviceQuoteById(Long id);
}
