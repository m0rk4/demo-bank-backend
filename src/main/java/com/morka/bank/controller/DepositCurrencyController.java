package com.morka.bank.controller;

import com.morka.bank.dto.DepositCurrencyDto;
import com.morka.bank.facade.DepositCurrencyFrontFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deposit-currencies")
public class DepositCurrencyController {

    private final DepositCurrencyFrontFacade facade;

    @GetMapping("/by-deposit-type")
    public List<DepositCurrencyDto> getDepositType(@RequestParam Long depositTypeId) {
        return facade.findByDepositTypeId(depositTypeId);
    }
}
