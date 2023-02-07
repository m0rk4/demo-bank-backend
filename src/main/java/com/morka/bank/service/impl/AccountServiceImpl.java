package com.morka.bank.service.impl;

import com.morka.bank.model.Account;
import com.morka.bank.model.AccountCode;
import com.morka.bank.repository.AccountRepository;
import com.morka.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public Page<Account> getAccounts(String name, Pageable pageable) {
        return repository.findAllByNameContainingOrCodeIn(name, AccountCode.getBankCodes(), pageable);
    }
}
