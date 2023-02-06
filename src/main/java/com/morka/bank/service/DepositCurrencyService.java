package com.morka.bank.service;

import com.morka.bank.dto.DepositCurrencyDto;

import java.util.List;

public interface DepositCurrencyService {

    List<DepositCurrencyDto> findByDepositTypeId(Long depositTypeId);
}
