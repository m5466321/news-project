package com.example.manager.aspect;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLogger {

    String value() default "";
    boolean consoleLog() default false;
}

