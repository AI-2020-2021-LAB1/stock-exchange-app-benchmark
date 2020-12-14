package org.project.benchmark.app.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class URLValidation implements ConstraintValidator<URL, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            java.net.URL url = new java.net.URL(value);
            url.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
