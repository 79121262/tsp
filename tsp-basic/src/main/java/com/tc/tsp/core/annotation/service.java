package com.tc.tsp.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by cai.tian on 2017/11/30.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface service {

    String version();

    int timeout() default -1;

    int threads() default -1;

    int failovertimes() default -1;
}
