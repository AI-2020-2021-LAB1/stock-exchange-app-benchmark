package org.project.benchmark.app.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = URLValidation.class)
@Documented
public @interface URL {
    String message() default "Given value is not valid URL";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
