package com.coder.dream.service.exception;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/2/4.
 */
@Singleton
@Service(version = "1.0.0")
public class CheckedExceptionImpl implements CheckedException {

    @Override
    public void run() throws MethodException {
        throw new MethodException("method exception");
    }
}
