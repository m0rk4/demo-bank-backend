package com.morka.bank.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DepositAgreementDto {

    private Long id;

    private String number;

    private LocalDate startDate;

    private LocalDate endDate;

    private DepositCurrencyLightDto depositCurrency;

    private ClientDto client;
}
