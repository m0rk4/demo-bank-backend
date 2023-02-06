package com.morka.bank.service.impl;

import com.morka.bank.model.DepositCurrency;
import com.morka.bank.repository.DepositCurrencyRepository;
import com.morka.bank.repository.DepositTypeRepository;
import com.morka.bank.service.DepositCurrencyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepositCurrencyServiceImpl implements DepositCurrencyService {

    private final DepositCurrencyRepository repository;

    private final DepositTypeRepository depositTypeRepository;

    @Override
    public List<DepositCurrency> findByDepositTypeId(Long depositTypeId) {
        return repository.findByDepositType(depositTypeRepository.getReferenceById(depositTypeId));
    }

    @Override
    public DepositCurrency findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No deposit currency found."));
    }
}
