package com.benjamin.E_invoice.exception;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(String message){
        super(message);
    }
}
