package com.morka.bank.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deposit-agreements")
public class DepositAgreementController {

    @PostMapping
    public Object create(@RequestBody Object dto) {
        return null;
    }
}
