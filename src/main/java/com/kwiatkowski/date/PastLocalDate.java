package com.kwiatkowski.date;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;

/**
 * Created by Rafał on 2017-03-22.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastLocalDate.PastValidator.class)
@Documented
public @interface PastLocalDate {
    String message() default "{javax.validation.constraints.Past.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    class PastValidator implements ConstraintValidator<PastLocalDate, LocalDate> {

        @Override
        public void initialize(PastLocalDate past) {

        }

        @Override
        public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
            return value == null || value.isBefore(LocalDate.now());
        }
    }
}
