package com.coder.dream.service.exception;

import com.alibaba.dubbo.config.annotation.Service;
import com.coder.dream.exceptions.ServiceException;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/2/4.
 */
@Singleton
@Service(version = "1.0.0")
public class NotCheckedExceptionServiceImpl implements NotCheckedExceptionService{

    @Override
    public void run() {
        throw new ServiceException("not checked service exception");
    }
}
