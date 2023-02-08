package com.morka.bank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositCurrencyLightDto {

    private CurrencyTypeDto currencyType;

    private DepositTypeDto depositType;
}
