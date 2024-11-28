package com.benjamin.E_invoice.config;

import com.benjamin.E_invoice.model.Customer;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    Customer customer;
    String message;
    String token;
}
