package org.step.service.constraints;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailConstraintValidator implements ConstraintValidator<EmailConstraint, String> {

    private static final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    private int min;
    private int max;

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        if (email.length() < this.min || email.length() > this.max) {
            return false;
        }
        Pattern validEmail = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

        Matcher emailMatcher = validEmail.matcher(email);

        return emailMatcher.find();
    }
}
