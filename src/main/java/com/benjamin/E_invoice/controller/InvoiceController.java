package com.benjamin.E_invoice.controller;

import com.benjamin.E_invoice.config.InvoiceResponse;
import com.benjamin.E_invoice.exception.CustomerNotFoundException;
import com.benjamin.E_invoice.exception.InvoiceNotFoundException;
import com.benjamin.E_invoice.model.Invoice;
import com.benjamin.E_invoice.model.Requests.InvoiceRequest;
import com.benjamin.E_invoice.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice")
@Slf4j
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/saveInvoice/{customerId}")
    public ResponseEntity<InvoiceResponse> saveInvoice(@RequestBody InvoiceRequest invoiceRequest,@PathVariable Long customerId){
        InvoiceResponse invoice=null;

        try {
            log.info("Saving Invoice for Customer with ID: {} {}",customerId,invoiceRequest);
            invoiceRequest.setCustomerId(customerId);
            invoice=invoiceService.saveInvoice(customerId,invoiceRequest);
            return ResponseEntity.ok(invoice);
        }catch (Exception e){
            log.warn("Error Saving Invoice",e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/allInvoices")
    public ResponseEntity<List<Invoice>> allInvoices(){
        List<Invoice> invoice=null;
        try {
            log.info("List of All Invoices");
            invoice=invoiceService.allInvoices();
            return ResponseEntity.ok(invoice);
        }catch (InvoiceNotFoundException e){
            throw new InvoiceNotFoundException("No Invoice Found");
        }
    }

    @GetMapping("/customerInvoices/{customerId}")
    public ResponseEntity<List<InvoiceResponse>> customerInvoices(@PathVariable Long customerId){
        List<InvoiceResponse> invoice=null;
        try {
            log.info("Search for invoices for Customer with ID: {}",customerId);
            invoice=invoiceService.findCustomerInvoices(customerId);
            return ResponseEntity.ok(invoice);
        }catch (CustomerNotFoundException e){
            log.warn("Customer with ID: {} Not Found",customerId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponse> invoiceById(@PathVariable Long invoiceId){
        InvoiceResponse invoice=null;
        try{
            log.info("Retrieving Invoice By ID: {}",invoiceId);
            invoice=invoiceService.findInvoiceById(invoiceId);
            return ResponseEntity.ok(invoice);
        }catch (InvoiceNotFoundException e){
            log.warn("Invoice with ID: {} Not Found",invoice);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateInvoice/{invoiceId}")
    public ResponseEntity<InvoiceResponse> updateInvoice(@PathVariable Long invoiceId,@RequestBody InvoiceRequest invoiceRequest){
        InvoiceResponse invoice=null;
        try{
            log.info("Updating Invoice with ID: {}",invoiceId);
            invoice=invoiceService.updateInvoice(invoiceId,invoiceRequest);
            return ResponseEntity.ok(invoice);
        }catch (InvoiceNotFoundException e){
            log.warn("Invoice with ID: {} Not Found",invoiceId);
            throw new InvoiceNotFoundException("Invoice with ID:"+invoiceId+" Not Found");
        }catch (RuntimeException e){
            log.error("Error Updating Invoice with ID: {}",invoiceId,e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponse> deleteInvoice(@PathVariable Long invoiceId){
        InvoiceResponse invoice=null;
        try {
            log.info("Deleting Invoice with ID: {}",invoiceId);
            invoice=invoiceService.deleteInvoice(invoiceId);
            return ResponseEntity.ok(invoice);
        }catch (InvoiceNotFoundException e){
            log.warn("Invoice with ID: {} Not Found",invoiceId,e);
            throw new InvoiceNotFoundException("Invoice with ID: "+invoiceId+ " Not Found");
        }catch (RuntimeException e){
            log.error("Error Deleting Invoice with ID: {}",invoiceId,e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
