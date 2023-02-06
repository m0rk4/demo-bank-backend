package com.morka.bank.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositCurrencyDto {

    private Long id;

    private CurrencyTypeDto currencyType;

    private boolean hasCapitalization;

    private Integer periodInDays;

    private Long minDepositSize;

    private boolean isRevocable;

    private BigDecimal percent;
}
