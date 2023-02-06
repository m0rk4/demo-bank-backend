package com.morka.bank.exception;

public class InvalidDepositBalanceException extends RuntimeException {
    public InvalidDepositBalanceException(String message) {
        super(message);
    }
}
