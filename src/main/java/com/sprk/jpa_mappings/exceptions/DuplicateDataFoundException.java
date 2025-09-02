package com.sprk.jpa_mappings.exceptions;

public class DuplicateDataFoundException extends RuntimeException {
    public DuplicateDataFoundException(String message) {
        super(message);
    }
}
