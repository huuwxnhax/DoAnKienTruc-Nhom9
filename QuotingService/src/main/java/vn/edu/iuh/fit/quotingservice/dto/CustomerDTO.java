package vn.edu.iuh.fit.quotingservice.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerDTO implements Serializable {
    private static final long serialVersionUIDLONG = 1L;
    private long id;
    private String name;
    private String address;
    private String phone;
    private String email;

    public CustomerDTO(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}
