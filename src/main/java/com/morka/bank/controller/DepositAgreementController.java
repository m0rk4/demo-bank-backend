package com.morka.bank.controller;

import com.morka.bank.dto.AddDepositAgreementDto;
import com.morka.bank.service.DepositAgreementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deposit-agreements")
public class DepositAgreementController {

    private final DepositAgreementService service;

    @PostMapping
    public void create(@RequestBody @Valid AddDepositAgreementDto dto) {
        service.createDepositAgreement(dto);
    }

    @PatchMapping("/finish")
    public void finish(@RequestParam LocalDate date) {
        service.finishAtDay(date);
    }
}
