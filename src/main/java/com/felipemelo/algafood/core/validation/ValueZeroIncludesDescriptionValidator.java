package com.felipemelo.algafood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ValueZeroIncludesDescriptionValidator implements ConstraintValidator<ValueZeroIncludesDescription,
        Object> {

    private String valueField;
    private String descriptionField;
    private String mandatoryDescription;

    @Override
    public void initialize(ValueZeroIncludesDescription constraintAnnotation) {
        this.valueField = constraintAnnotation.valueField();
        this.descriptionField = constraintAnnotation.descriptionField();
        this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        try{
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(o.getClass(), valueField)
                    .getReadMethod().invoke(o);
            String description = (String) BeanUtils.getPropertyDescriptor(o.getClass(), descriptionField)
                    .getReadMethod().invoke(o);

            if (value != null && description != null && BigDecimal.ZERO.compareTo(value) == 0) {
                valid = description.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
            }
        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return valid;
    }
}
