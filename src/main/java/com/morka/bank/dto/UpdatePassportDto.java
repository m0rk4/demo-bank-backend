package com.morka.bank.dto;

import com.morka.bank.validators.PassportIdConstraint;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdatePassportDto {

    @NotBlank
    @PassportIdConstraint
    private String passportId;

    @NotBlank
    @Pattern(regexp = "(AB|BM|HB|KH|MP|MC|KB|PP|SP|DP)", message = "Not a valid password series.")
    private String passportSeries;

    @NotBlank
    @Pattern(regexp = "\\d{7}", message = "Not a valid password number.")
    private String passportNumber;

    @NotBlank
    @Size(max = 255)
    private String passportIssuer;

    @NotNull
    @PastOrPresent
    private LocalDate passportIssuedDate;

    @NotBlank
    @Size(max = 255)
    private String passportAddress;

    @NotBlank
    @Size(max = 255)
    private String placeOfBirth;
}
