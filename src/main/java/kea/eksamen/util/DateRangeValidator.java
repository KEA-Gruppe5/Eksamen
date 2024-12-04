package kea.eksamen.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kea.eksamen.dto.DateRange;

public class DateRangeValidator implements ConstraintValidator<EndDateAfterStartDate, DateRange> {
    @Override
    public void initialize(EndDateAfterStartDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(DateRange dateRange, ConstraintValidatorContext constraintValidatorContext) {
        return dateRange.getEndDate().isAfter(dateRange.getStartDate());
    }
}
