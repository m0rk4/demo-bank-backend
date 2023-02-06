package com.morka.bank.service.impl;

import com.morka.bank.dto.DepositCurrencyDto;
import com.morka.bank.mapper.Mapper;
import com.morka.bank.repository.DepositCurrencyRepository;
import com.morka.bank.repository.DepositTypeRepository;
import com.morka.bank.service.DepositCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositCurrencyServiceImpl implements DepositCurrencyService {

    private final Mapper mapper;

    private final DepositCurrencyRepository repository;

    private final DepositTypeRepository depositTypeRepository;

    @Override
    public List<DepositCurrencyDto> findByDepositTypeId(Long depositTypeId) {
        var depositCurrencies = repository.findByDepositType(depositTypeRepository.getReferenceById(depositTypeId));
        return depositCurrencies.stream()
                .map(depositCurrency -> mapper.map(depositCurrency, DepositCurrencyDto.class))
                .toList();
    }
}
