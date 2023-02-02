package com.morka.bank.controller;


import com.morka.bank.model.City;
import com.morka.bank.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {

    private final CityRepository cityRepository;

    @GetMapping
    public List<City> getCities() {
        return cityRepository.findAll();
    }
}
