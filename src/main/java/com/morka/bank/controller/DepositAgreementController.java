package com.morka.bank.controller;

import com.morka.bank.dto.AddDepositAgreementDto;
import com.morka.bank.dto.DepositAgreementDto;
import com.morka.bank.service.DepositAgreementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    public Page<DepositAgreementDto> getAgreements(Pageable pageable) {
        return service.getAgreements(pageable);
    }

    @PostMapping
    public void create(@RequestBody @Valid AddDepositAgreementDto dto) {
        service.createDepositAgreement(dto);
    }

    @PatchMapping("/finish")
    public void finish(@RequestParam LocalDate date) {
        service.finishAtDay(date);
    }

    @PatchMapping("/{id}/close")
    public void close(@PathVariable Long id) {
        service.close(id);
    }
}
