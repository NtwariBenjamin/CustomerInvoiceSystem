package com.benjamin.E_invoice.exception;

public  class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}

