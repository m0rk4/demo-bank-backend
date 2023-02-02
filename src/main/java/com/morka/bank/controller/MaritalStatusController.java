
package com.morka.bank.controller;


import com.morka.bank.model.MaritalStatus;
import com.morka.bank.repository.MaritalStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marital-statuses")
public class MaritalStatusController {

    private final MaritalStatusRepository repository;

    @GetMapping
    public List<MaritalStatus> getDisabilities() {
        return repository.findAll();
    }
}
