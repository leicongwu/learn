package com.lifestorm.learn.frame.dubbo.validation.support;

import com.lifestorm.learn.frame.dubbo.validation.Validation;
import com.lifestorm.learn.frame.dubbo.validation.Validator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by life_storm on 2018/9/25.
 */
public abstract class AbstractValidator implements Validation{

    private ConcurrentHashMap<String,Validator> validators = new ConcurrentHashMap<String, Validator>();

    @Override
    public Validator getValidator(String url) {
        String key = url;
        Validator validator = this.validators.get(key);
        if(validator == null ){
            this.validators.put(key,createValidator(url));
        }
        return null;
    }

    protected abstract Validator createValidator(String url);
}
