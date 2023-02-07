package com.morka.bank.controller;


import com.morka.bank.dto.AccountDto;
import com.morka.bank.facade.AccountFrontFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountFrontFacade facade;

    @GetMapping
    public Page<AccountDto> getClients(@RequestParam String name, Pageable pageable) {
        return facade.getAccounts(name, pageable);
    }
}
