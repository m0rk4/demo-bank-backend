package com.morka.bank.controller;


import com.morka.bank.model.Disability;
import com.morka.bank.repository.DisabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/disabilities")
public class DisabilityController {

    private final DisabilityRepository repository;

    @GetMapping
    public List<Disability> getDisabilities() {
        return repository.findAll();
    }
}
