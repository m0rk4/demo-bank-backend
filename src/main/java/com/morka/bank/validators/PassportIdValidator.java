package com.morka.bank.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Map;

public class PassportIdValidator implements ConstraintValidator<PassportIdConstraint, String> {

    private static final String PATTERN = "[1-6]\\d{6}[ABCKEMH]\\d{3}(PB|BA|BI)\\d";

    @Override
    public void initialize(PassportIdConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() != 14) {
            return false;
        }
        if (!value.matches(PATTERN)) {
            return false;
        }
        var firstDigitToYears = Map.of(
                1, 1800,
                2, 1800,
                3, 1900,
                4, 1900,
                5, 2000,
                6, 2000
        );
        var centuryYears = firstDigitToYears.get(Integer.parseInt(value.substring(0, 1)));
        var day = Integer.parseInt(value.substring(1, 3));
        var month = Integer.parseInt(value.substring(3, 5));
        var year = centuryYears + Integer.parseInt(value.substring(5, 7));
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException ex) {
            return false;
        }
    }
}
