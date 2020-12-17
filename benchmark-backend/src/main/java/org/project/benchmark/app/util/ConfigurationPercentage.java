package org.project.benchmark.app.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = ConfigurationPercentageValidation.class)
@Documented
public @interface ConfigurationPercentage {
    String message() default "Given values are not valid parameters for configuration.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
