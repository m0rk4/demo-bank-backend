package com.morka.bank.exception;

public class PassportIdExistsException extends RuntimeException {
    public PassportIdExistsException(String message) {
        super(message);
    }
}
