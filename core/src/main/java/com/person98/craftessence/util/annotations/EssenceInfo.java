package com.person98.craftessence.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EssenceInfo {

    String version();

    String author();

    String name();

    String description();

    String[] internalDependencies() default {};

    String[] externalDependencies() default {};
}
