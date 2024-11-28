package com.benjamin.E_invoice.service;

import com.benjamin.E_invoice.config.InvoiceResponse;
import com.benjamin.E_invoice.exception.CustomerNotFoundException;
import com.benjamin.E_invoice.model.Customer;
import com.benjamin.E_invoice.model.Invoice;
import com.benjamin.E_invoice.model.Requests.InvoiceRequest;
import com.benjamin.E_invoice.repository.CustomerRepository;
import com.benjamin.E_invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public InvoiceResponse saveInvoice(Long customerId,InvoiceRequest invoiceRequest) {

        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()->new IllegalArgumentException("Customer with ID: "+invoiceRequest.getCustomerId()+" not found"));

        Invoice invoice=Invoice.builder()
                .customer(customer)
                .invoiceDate(invoiceRequest.getInvoiceDate())
                .amount(invoiceRequest.getAmount())
                .status(invoiceRequest.getStatus())
                .build();
        invoiceRepository.save(invoice);
        return InvoiceResponse.builder().invoice(invoice).message("Invoice Saved Successfully!").build();
    }

    public List<Invoice> allInvoices() {
        return invoiceRepository.findAll();
    }

    public List<InvoiceResponse> findCustomerInvoices(Long customerId) {
       Customer customer=customerRepository.findById(customerId)
               .orElseThrow(()-> new CustomerNotFoundException("Customer with ID:"+customerId+" not found"));
       List<Invoice> invoices=invoiceRepository.findByCustomerId(customerId);
       return invoices.stream()
               .map(invoice -> InvoiceResponse.builder()
                       .invoice(invoice)
                       .message("Invoice found for Customer with ID:"+customerId)
                       .build())
               .toList();

    }

    public InvoiceResponse findInvoiceById(Long invoiceId) {
        Optional<Invoice> invoice=invoiceRepository.findById(invoiceId);
        if (invoice.isEmpty()){
            return InvoiceResponse.builder()
                    .invoice(null)
                    .message("Invoice with ID "+invoiceId+" Not Found")
                    .build();
        }
        return InvoiceResponse.builder()
                .message("Invoice With ID:"+invoiceId+" Is Found")
                .invoice(invoice.get())
                .build();

    }

    public InvoiceResponse updateInvoice(Long invoiceId, InvoiceRequest invoiceRequest) {
        Optional<Invoice> invoice=invoiceRepository.findById(invoiceId);
        if (invoice.isEmpty()){
            return InvoiceResponse.builder()
                    .invoice(null)
                    .message("Invoice with ID:"+ invoiceId+" Not Found")
                    .build();
        }
        Invoice updateInvoice=invoice.get();
        updateInvoice.setInvoiceDate(invoiceRequest.getInvoiceDate());
        updateInvoice.setCustomer(updateInvoice.getCustomer());
        updateInvoice.setAmount(invoiceRequest.getAmount());
        updateInvoice.setStatus(invoiceRequest.getStatus());

        invoiceRepository.save(updateInvoice);
        return InvoiceResponse.builder()
                .message("Invoice with ID:"+invoiceId+" Updated Successfully!")
                .invoice(updateInvoice)
                .build();
    }

    public InvoiceResponse deleteInvoice(Long invoiceId) {
        Optional<Invoice> invoice=invoiceRepository.findById(invoiceId);
        if (invoice.isEmpty()){
            return InvoiceResponse.builder()
                    .invoice(null)
                    .message("Invoice With ID: "+invoiceId+" Not Found")
                    .build();
        }
        invoiceRepository.deleteById(invoiceId);
        return InvoiceResponse.builder()
                .message("Invoice With Id:"+invoiceId+" Deleted Successfully!")
                .invoice(invoice.get())
                .build();
    }
}
