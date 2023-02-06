package com.morka.bank.facade.impl;

import com.morka.bank.dto.DepositCurrencyDto;
import com.morka.bank.facade.DepositCurrencyFrontFacade;
import com.morka.bank.mapper.Mapper;
import com.morka.bank.service.DepositCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositCurrencyFacadeImpl implements DepositCurrencyFrontFacade {

    private final Mapper mapper;

    private final DepositCurrencyService service;

    @Override
    public List<DepositCurrencyDto> findByDepositTypeId(Long depositTypeId) {
        return service.findByDepositTypeId(depositTypeId).stream()
                .map(depositCurrency -> mapper.map(depositCurrency, DepositCurrencyDto.class))
                .toList();
    }
}
