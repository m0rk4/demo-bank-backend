package com.morka.bank.facade;

import com.morka.bank.dto.DepositCurrencyDto;

import java.util.List;

public interface DepositCurrencyFrontFacade {

    List<DepositCurrencyDto> findByDepositTypeId(Long depositTypeId);
}
