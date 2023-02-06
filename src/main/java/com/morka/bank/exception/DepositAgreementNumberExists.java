package com.morka.bank.exception;

public class DepositAgreementNumberExists extends RuntimeException {
    public DepositAgreementNumberExists(String message) {
        super(message);
    }
}
