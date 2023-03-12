package com.wd.weatherservice.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static java.time.LocalDate.now;

public class MaxDateValidator implements ConstraintValidator<MaxDate, LocalDate> {

    private static final LocalDate FURTHEST_POSSIBLE_DATE = now().plusDays(16);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isBefore(FURTHEST_POSSIBLE_DATE);
    }
}
