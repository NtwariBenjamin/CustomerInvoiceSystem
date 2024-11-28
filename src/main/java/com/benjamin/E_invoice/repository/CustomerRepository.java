package com.benjamin.E_invoice.repository;

import com.benjamin.E_invoice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String email);

    Customer findByEmail(String email);
}
