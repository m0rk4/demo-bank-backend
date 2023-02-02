package com.morka.bank.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneHomeNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneHomeNumberConstraint {
    String message() default "Invalid phone home number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
