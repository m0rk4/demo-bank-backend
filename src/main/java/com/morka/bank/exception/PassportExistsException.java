package com.morka.bank.exception;

public class PassportExistsException extends RuntimeException {
    public PassportExistsException(String message) {
        super(message);
    }
}
