package com.project.benchmark.algorithm.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to change name of filter (by default it sets filter name
 * as variable name, but some of them, like date<, cannot be written
 * in plain Java. This annotation resolves this problem
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryString {
    /**
     * Query parameter name
     */
    String value();
}
