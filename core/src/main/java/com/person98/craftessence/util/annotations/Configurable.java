package com.person98.craftessence.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  // The annotation will be available at runtime
@Target(ElementType.TYPE)  // The annotation can only be applied to classes
public @interface Configurable {
    String fileName();  // Define the fileName parameter for the annotation
}