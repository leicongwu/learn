package com.lcw.learn.frame.dubbo.validation.support.jvalidator;

import com.lcw.learn.frame.dubbo.validation.Validator;
import com.lcw.learn.frame.dubbo.validation.support.AbstractValidator;

/**
 * Created by engle on 2018/9/25.
 */
public class JValidation extends AbstractValidator {

    @Override
    protected Validator createValidator(String url) {
        return new JValidator(url);
    }
}
