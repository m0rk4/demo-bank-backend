package com.morka.bank.model;

import java.util.List;

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

    public static List<AccountCode> getBankCodes() {
        return List.of(AccountCode.BANK_CASH, AccountCode.BANK_DEVELOPMENT_FUND);
    }
}
