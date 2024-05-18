package vn.edu.iuh.fit.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.customerservice.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
