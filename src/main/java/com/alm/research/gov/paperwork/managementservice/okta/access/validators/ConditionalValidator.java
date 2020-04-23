package com.alm.research.gov.paperwork.managementservice.okta.access.validators;

import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

import static org.springframework.util.ObjectUtils.isEmpty;

public class ConditionalValidator implements ConstraintValidator<Conditional, Object> {
    private String selected;
    private String[] required;
    private String message;
    private String[] values;

    @Override
    public void initialize(Conditional constraintAnnotation) {
        selected = constraintAnnotation.selected();
        required = constraintAnnotation.required();
        message = constraintAnnotation.message();
        values = constraintAnnotation.values();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean valid = true;

        Object checkedValue = BeanUtils.getProperty(object, selected);

        if (Arrays.asList(values).contains(checkedValue)) {
            for (String propName : required) {
                Object requiredValue = BeanUtils.getProperty(object, propName);
                boolean propValid = requiredValue != null && !isEmpty(requiredValue);
                if (!propValid) {
                    valid = false;
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(propName + message).addPropertyNode(propName).addConstraintViolation();
                }
            }
        }
        return valid;
    }
}
