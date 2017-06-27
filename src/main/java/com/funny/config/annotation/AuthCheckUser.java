package com.funny.config.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by liuxin on 2017/6/27.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AuthCheckUser {
    boolean check() default false;

}
