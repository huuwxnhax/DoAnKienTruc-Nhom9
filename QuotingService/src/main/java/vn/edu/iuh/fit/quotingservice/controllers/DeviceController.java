package vn.edu.iuh.fit.quotingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.quotingservice.entity.Device;
import vn.edu.iuh.fit.quotingservice.service.DeviceService;
import vn.edu.iuh.fit.quotingservice.service.QuoteService;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;
    private final QuoteService quoteService;

    @Autowired
    public DeviceController(DeviceService deviceService, QuoteService quoteService) {
        this.deviceService = deviceService;
        this.quoteService = quoteService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createDevice(@RequestBody Device device) {
        Device devicecreated = deviceService.createDeviceQuote(device);
        quoteService.createQuote(devicecreated);
        return new ResponseEntity<>("Device created successfully.", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable Long id) {
        Device device = deviceService.getDeviceQuoteById(id);
        if (device != null) {
            return new ResponseEntity<>(device, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDeviceQuote(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
