package com.lcw.learn.frame.dubbo.validation;

import com.lcw.learn.frame.dubbo.common.extension.Adaptive;
import com.lcw.learn.frame.dubbo.common.extension.SPI;

/**
 * Created by engle on 2018/9/25.
 */
@SPI("jvalidation")
public interface Validation {

    @Adaptive({"validation"})
    Validator getValidator(String url);
}
