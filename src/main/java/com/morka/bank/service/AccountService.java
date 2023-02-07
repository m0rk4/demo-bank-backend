package com.morka.bank.service;

import com.morka.bank.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Page<Account> getAccounts(String name, Pageable pageable);
}
