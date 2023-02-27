package com.wd.weatherservice.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static java.time.LocalDate.now;

public class MaxDateValidator implements ConstraintValidator<MaxDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate furthestPossibleDate = now().plusDays(16);
        return localDate.isBefore(furthestPossibleDate);
    }
}
