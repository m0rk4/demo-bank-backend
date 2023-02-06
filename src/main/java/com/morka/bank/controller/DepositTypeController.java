package com.morka.bank.controller;

import com.morka.bank.dto.DepositTypeDto;
import com.morka.bank.service.DepositTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deposit-types")
public class DepositTypeController {

    private final DepositTypeService service;

    @GetMapping
    public Page<DepositTypeDto> create(Pageable pageable) {
        return service.getTypes(pageable);
    }
}
