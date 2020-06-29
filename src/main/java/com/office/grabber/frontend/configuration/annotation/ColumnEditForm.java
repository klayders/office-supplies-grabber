package com.office.grabber.frontend.configuration.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(
    {
        ElementType.FIELD,
        ElementType.METHOD
    }
)
public @interface ColumnEditForm {

  int displayOrder() default 999;

}
