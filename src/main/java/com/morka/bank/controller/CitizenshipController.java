package com.morka.bank.controller;


import com.morka.bank.model.Citizenship;
import com.morka.bank.repository.CitizenshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/citizenships")
public class CitizenshipController {

    private final CitizenshipRepository repository;

    @GetMapping
    public List<Citizenship> getCitizenships() {
        return repository.findAll();
    }
}
