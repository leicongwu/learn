package com.lifestorm.learn.frame.dubbo.common.extension;

import java.lang.annotation.*;

/**
 * Created by life_storm on 2018/9/25.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Adaptive {
    String[] value() default {};
}
