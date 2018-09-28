package com.lifestorm.learn.frame.dubbo.validation;

import com.lifestorm.learn.frame.dubbo.common.extension.Adaptive;
import com.lifestorm.learn.frame.dubbo.common.extension.SPI;

/**
 * Created by life_storm on 2018/9/25.
 */
@SPI("jvalidation")
public interface Validation {

    @Adaptive({"validation"})
    Validator getValidator(String url);
}
