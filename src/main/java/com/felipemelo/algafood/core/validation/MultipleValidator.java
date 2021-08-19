package com.felipemelo.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private int multipleNumber;

    @Override
    public void initialize(Multiple constraintAnnotation) {
        this.multipleNumber = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        var valid = true;

        if (number != null){
            var decimalValue = BigDecimal.valueOf(number.doubleValue());
            var decimalMultiple = BigDecimal.valueOf(multipleNumber);
            var rest = decimalValue.remainder(decimalMultiple);

            valid = BigDecimal.ZERO.compareTo(rest) == 0;
        }

        return valid;
    }
}
