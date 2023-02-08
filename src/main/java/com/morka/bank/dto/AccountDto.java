package com.morka.bank.dto;

import com.morka.bank.model.AccountActivity;
import com.morka.bank.model.AccountCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AccountDto {

    private Long id;

    private String number;

    private AccountCode code;

    private AccountActivity activity;

    private Long debit;

    private Long credit;

    private String name;

    private CurrencyTypeDto currencyType;

    private Instant expiredTs;
}
