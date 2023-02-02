package com.morka.bank.dto;

import com.morka.bank.model.Sex;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateClientDto {

    private String firstname;

    private String lastname;

    private String patronymic;

    private LocalDate dateOfBirth;

    private Sex sex;

    private String address;

    private String phoneNumberHome;

    private String phoneNumber;

    private String email;

    private boolean isRetired;

    private Integer monthlyIncome;

    private UpdatePassportDto passport;

    private Long cityActualId;

    private Long maritalStatusId;

    private Long citizenshipId;

    private Long disabilityId;
}
