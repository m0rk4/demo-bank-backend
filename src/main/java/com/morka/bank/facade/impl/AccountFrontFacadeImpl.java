package com.morka.bank.facade.impl;

import com.morka.bank.dto.AccountDto;
import com.morka.bank.facade.AccountFrontFacade;
import com.morka.bank.mapper.Mapper;
import com.morka.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountFrontFacadeImpl implements AccountFrontFacade {

    private final Mapper mapper;

    private final AccountService accountService;

    @Override
    public Page<AccountDto> getAccounts(String name, Pageable pageable) {
        var page = accountService.getAccounts(name, pageable);
        return page.map(account -> mapper.map(account, AccountDto.class));
    }
}
