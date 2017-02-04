package com.coder.dream.service.exceptions;

import com.alibaba.dubbo.config.annotation.Reference;
import com.coder.dream.guice.lifecycle.Start;
import com.coder.dream.service.exception.CheckedException;
import com.coder.dream.service.exception.ConvertToRpcExceptionService;
import com.coder.dream.service.exception.JdkExceptionService;
import com.coder.dream.service.exception.NotCheckedExceptionService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/1/19.
 */
@Singleton
public class ExceptionEntrance {

    @Reference(version = "1.0.0")
    private CheckedException checkedException;

    @Reference(version = "1.0.0")
    private ConvertToRpcExceptionService convertToRpcExceptionService;

    @Reference(version = "1.0.0")
    private JdkExceptionService jdkExceptionService;

    @Reference(version = "1.0.0")
    private NotCheckedExceptionService notCheckedExceptionService;

    @Start
    public void test(){
//        try {
//            checkedException.run();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
//            convertToRpcExceptionService.run();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
//            jdkExceptionService.run();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        try {
            notCheckedExceptionService.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
