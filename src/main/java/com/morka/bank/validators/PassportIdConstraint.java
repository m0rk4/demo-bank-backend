package com.morka.bank.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PassportIdValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassportIdConstraint {
    String message() default "Invalid passport id.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
