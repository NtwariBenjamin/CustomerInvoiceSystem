package com.benjamin.E_invoice.config;

import com.benjamin.E_invoice.model.Invoice;
import lombok.*;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponse {
    Invoice invoice;
    String message;
}
