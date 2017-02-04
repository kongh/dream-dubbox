package com.coder.dream.exceptions;

/**
 * Dubbo对该异常转化成Rpc exception
 * Created by konghang on 2017/2/4.
 */
public class ConvertToRpcException extends RuntimeException{

    public ConvertToRpcException() {
        super();
    }

    public ConvertToRpcException(String message) {
        super(message);
    }

    public ConvertToRpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertToRpcException(Throwable cause) {
        super(cause);
    }

    protected ConvertToRpcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
