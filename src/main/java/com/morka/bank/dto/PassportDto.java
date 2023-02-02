package com.morka.bank.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PassportDto {

    private Long id;

    private String passportId;

    private String passportSeries;

    private String passportNumber;

    private String passportIssuer;

    private LocalDate passportIssuedDate;

    private String passportAddress;

    private String placeOfBirth;
}
