package com.coder.dream.service.exception;

import com.alibaba.dubbo.config.annotation.Service;
import com.coder.dream.exceptions.ConvertToRpcException;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/2/4.
 */
@Singleton
@Service(version = "1.0.0")
public class ConvertToRpcExceptionServiceImpl implements ConvertToRpcExceptionService {

    @Override
    public void run() {
        throw new ConvertToRpcException("this method exception will convert to dubbo Rpc exception");
    }
}
