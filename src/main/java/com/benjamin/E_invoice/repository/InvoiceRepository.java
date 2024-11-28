package com.benjamin.E_invoice.repository;

import com.benjamin.E_invoice.config.InvoiceResponse;
import com.benjamin.E_invoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> findByCustomerId(Long customerId);

}
