package kea.eksamen.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kea.eksamen.dto.DateRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateRangeValidator implements ConstraintValidator<EndDateAfterStartDate, DateRange> {

    private static final Logger logger = LoggerFactory.getLogger(DateRangeValidator.class);
    @Override
    public void initialize(EndDateAfterStartDate constraintAnnotation) {

    }

    @Override
    public boolean isValid(DateRange dateRange, ConstraintValidatorContext constraintValidatorContext) {
        logger.info("Validating date range: {}", dateRange);
        return dateRange.getEndDate().isAfter(dateRange.getStartDate());
    }
}
