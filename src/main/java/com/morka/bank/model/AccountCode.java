package com.morka.bank.model;

public enum AccountCode {
    BANK_CASH("1010"),
    BANK_DEVELOPMENT_FUND("7324"),
    INDIVIDUAL("3014");

    private final String code;

    AccountCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
