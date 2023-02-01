package com.morka.bank.dto;

import com.morka.bank.model.Sex;

import java.time.LocalDate;

public record ClientDto(
        Long id,
        String firstname,
        String lastname,
        String patronymic,
        LocalDate dateOfBirth,
        Sex sex,
        String address,
        String cityName,
        String disabilityName,
        String citizenshipName,
        String maritalStatusName,
        boolean isRetired,
        String email) {

}
