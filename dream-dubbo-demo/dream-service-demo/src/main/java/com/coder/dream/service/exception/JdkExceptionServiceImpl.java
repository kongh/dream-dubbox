package com.coder.dream.service.exception;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/2/4.
 */
@Singleton
@Service(version = "1.0.0")
public class JdkExceptionServiceImpl implements JdkExceptionService {

    private NotCheckedExceptionService notCheckedExceptionService;

    @Override
    public void run() {
        //will throw jdk null pointer exception
        notCheckedExceptionService.run();
    }
}
