package com.coder.dream.service.exception;

/**
 * 方法显式声明异常
 *
 * Created by konghang on 2017/2/4.
 */
public class MethodException extends RuntimeException{

    public MethodException() {
    }

    public MethodException(String message) {
        super(message);
    }

    public MethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodException(Throwable cause) {
        super(cause);
    }

    public MethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
