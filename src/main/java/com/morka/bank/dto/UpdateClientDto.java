package com.morka.bank.dto;

import com.morka.bank.model.Sex;
import com.morka.bank.validators.PhoneHomeNumberConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateClientDto {

    private static final String PHONE_NUMBER_PATTERN = "(25\\d{7}|291\\d{6}|292\\d{6}|293\\d{6}|295\\d{6}|296\\d{6}|297\\d{6}|298\\d{6}|299\\d{6}|33\\d{7}|44\\d{7})";

    @Pattern(regexp = "[a-zA-Zа-яА-ЯёЁ]+", message = "Not a valid firstname.")
    @NotBlank
    @Size(max = 255)
    private String firstname;

    @Pattern(regexp = "[a-zA-Zа-яА-ЯёЁ]+", message = "Not a valid lastname.")
    @NotBlank
    @Size(max = 255)
    private String lastname;

    @Pattern(regexp = "[a-zA-Zа-яА-ЯёЁ]+", message = "Not a valid patronymic.")
    @NotBlank
    @Size(max = 255)
    private String patronymic;

    @NotNull
    @PastOrPresent
    private LocalDate dateOfBirth;

    @NotNull
    private Sex sex;

    @NotBlank
    @Size(max = 255)
    private String address;

    @NotBlank
    @PhoneHomeNumberConstraint
    private String phoneNumberHome;

    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = "Not a valid phone number.")
    @NotBlank
    private String phoneNumber;

    @Email
    private String email;

    @NotNull
    private boolean isRetired;

    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer monthlyIncome;

    @Valid
    private UpdatePassportDto passport;

    @NotNull
    private Long cityActualId;

    @NotNull
    private Long maritalStatusId;

    @NotNull
    private Long citizenshipId;

    @NotNull
    private Long disabilityId;
}
