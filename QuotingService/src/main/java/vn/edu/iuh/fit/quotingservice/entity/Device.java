package vn.edu.iuh.fit.quotingservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter @ToString
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namedevice;
    private String type;
    private String material;
    private double weight;
    private String manufacturer;
    private String model;
    private double condition;
    private int usageDay;


    @Column(unique = true)
    private String serialNumber;

    private Long customerId;


    public Device(String namedevice, String type, String material, double weight, String manufacturer, String model, double condition, int usageDay, String serialNumber) {
        this.namedevice = namedevice;
        this.type = type;
        this.material = material;
        this.weight = weight;
        this.manufacturer = manufacturer;
        this.model = model;
        this.condition = condition;
        this.usageDay = usageDay;
        this.serialNumber = serialNumber;
    }

    public Device(String namedevice, String type, String material, double weight, String manufacturer, String model, double condition, int usageDay, String serialNumber, Long customerId) {
        this.namedevice = namedevice;
        this.type = type;
        this.material = material;
        this.weight = weight;
        this.manufacturer = manufacturer;
        this.model = model;
        this.condition = condition;
        this.usageDay = usageDay;
        this.serialNumber = serialNumber;
        this.customerId = customerId;
    }
}
