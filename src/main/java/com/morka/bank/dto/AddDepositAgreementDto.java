package com.morka.bank.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddDepositAgreementDto {

    private static final String NUMBER_PATTERN = "\\d{9}";

    @NotBlank
    @Pattern(regexp = NUMBER_PATTERN)
    private String number;

    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    @Max(Long.MAX_VALUE)
    @Min(0)
    private Long depositBalance;

    @NotNull
    private Long depositCurrencyId;

    @NotNull
    private Long clientId;
}
