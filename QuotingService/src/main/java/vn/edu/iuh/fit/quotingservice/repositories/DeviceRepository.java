package vn.edu.iuh.fit.quotingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.quotingservice.entity.Device;

public interface DeviceRepository extends JpaRepository<Device,Long> {
    Device findBySerialNumber(String serialNumber);
}
