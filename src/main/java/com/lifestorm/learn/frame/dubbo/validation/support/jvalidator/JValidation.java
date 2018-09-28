package com.lifestorm.learn.frame.dubbo.validation.support.jvalidator;

import com.lifestorm.learn.frame.dubbo.validation.Validator;
import com.lifestorm.learn.frame.dubbo.validation.support.AbstractValidator;

/**
 * Created by life_storm on 2018/9/25.
 */
public class JValidation extends AbstractValidator {

    @Override
    protected Validator createValidator(String url) {
        return new JValidator(url);
    }
}
