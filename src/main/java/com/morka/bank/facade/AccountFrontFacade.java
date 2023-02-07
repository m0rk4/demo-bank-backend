package com.morka.bank.facade;

import com.morka.bank.dto.AccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountFrontFacade {
    Page<AccountDto> getAccounts(String name, Pageable pageable);
}
