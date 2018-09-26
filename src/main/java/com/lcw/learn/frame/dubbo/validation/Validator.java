package com.lcw.learn.frame.dubbo.validation;

/**
 * Created by engle on 2018/9/25.
 */
public interface Validator {

    void validate(String param, Class<?> params[], Object[] objects) throws Exception;

}
