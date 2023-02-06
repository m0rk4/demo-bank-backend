package com.morka.bank.service;

import com.morka.bank.model.DepositCurrency;

import java.util.List;

public interface DepositCurrencyService {

    List<DepositCurrency> findByDepositTypeId(Long depositTypeId);

    DepositCurrency findById(Long id);
}
