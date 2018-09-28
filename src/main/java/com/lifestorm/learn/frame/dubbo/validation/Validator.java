package com.lifestorm.learn.frame.dubbo.validation;

/**
 * Created by life_storm on 2018/9/25.
 */
public interface Validator {

    void validate(String param, Class<?> params[], Object[] objects) throws Exception;

}
