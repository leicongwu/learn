package com.lcw.learn.frame.dubbo.common.extension;

import java.lang.annotation.*;

/**
 * Created by engle on 2018/9/25.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI {
    String value() default "";
}
