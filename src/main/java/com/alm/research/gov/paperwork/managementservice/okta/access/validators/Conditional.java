package com.alm.research.gov.paperwork.managementservice.okta.access.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Repeatable(Conditionals.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConditionalValidator.class})
public @interface Conditional {

    String message() default " is required.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String selected();
    String[] required();
    String[] values();
}