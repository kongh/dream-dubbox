package com.coder.dream.service.versions;

import com.alibaba.dubbo.config.annotation.Reference;
import com.coder.dream.service.versions.HelloWorldService;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/1/19.
 */
@Singleton
public class JimGreetService {

    @Reference
    private HelloWorldService helloWorldServiceWithNoVersion;

    @Reference(version = "1")
    private HelloWorldService helloWorldServiceWithV1;

    @Reference(version = "2")
    private HelloWorldService helloWorldServiceWithV2;

    public void say(){
        System.out.println(helloWorldServiceWithNoVersion.greet("Jim"));

        System.out.println(helloWorldServiceWithV1.greet("Jim"));

        System.out.println(helloWorldServiceWithV2.greet("Jim"));
    }
}
