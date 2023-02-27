package com.wd.weatherservice.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MaxDateValidator.class)
public @interface MaxDate {

    String message() default "We do not support such a distant date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
