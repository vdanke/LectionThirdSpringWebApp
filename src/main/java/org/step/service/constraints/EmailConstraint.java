package org.step.service.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {EmailConstraintValidator.class})
public @interface EmailConstraint {

    String message() default "Invalid email";
    int min() default 5;
    int max() default 254;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
