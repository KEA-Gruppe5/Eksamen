package kea.eksamen.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EndDateAfterStartDate {
    String message() default "End date must be after the start date";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
