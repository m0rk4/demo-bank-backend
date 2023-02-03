package com.morka.bank.exception;

public class PassportNumberExistsException extends RuntimeException {
    public PassportNumberExistsException(String message) {
        super(message);
    }
}
