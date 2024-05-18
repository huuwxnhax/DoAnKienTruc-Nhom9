package vn.edu.iuh.fit.quotingservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter @ToString
@Table(name ="quote")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double proposedPrice;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;


    public Quote(double proposedPrice, Device device) {
        this.proposedPrice = proposedPrice;
        this.device = device;
    }

    public Quote(double proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

}
