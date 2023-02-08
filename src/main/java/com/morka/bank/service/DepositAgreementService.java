package com.morka.bank.service;

import com.morka.bank.dto.AddDepositAgreementDto;
import com.morka.bank.dto.DepositAgreementDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface DepositAgreementService {

    void createDepositAgreement(AddDepositAgreementDto dto);

    void finishAtDay(LocalDate date);

    Page<DepositAgreementDto> getAgreements(Pageable pageable);

    void close(Long id);
}
