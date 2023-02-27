package com.wd.weatherservice.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MaxDateValidator implements ConstraintValidator<MaxDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate furthestPossibleDate = LocalDate.now().plus(16, ChronoUnit.DAYS);
        return localDate.isBefore(furthestPossibleDate) && (localDate.isEqual(LocalDate.now()) || localDate.isAfter(LocalDate.now()));
    }
}
