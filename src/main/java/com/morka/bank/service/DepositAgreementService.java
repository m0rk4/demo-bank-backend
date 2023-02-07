package com.morka.bank.service;

import com.morka.bank.dto.AddDepositAgreementDto;

import java.time.LocalDate;

public interface DepositAgreementService {

    void createDepositAgreement(AddDepositAgreementDto dto);

    void finishAtDay(LocalDate date);
}
