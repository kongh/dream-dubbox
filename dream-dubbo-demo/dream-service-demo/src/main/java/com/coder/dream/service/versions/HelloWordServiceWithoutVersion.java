package com.coder.dream.service.versions;

import com.alibaba.dubbo.config.annotation.Service;
import com.coder.dream.service.versions.HelloWorldService;
import com.google.inject.Singleton;

/**
 * 打招呼服务实现没有版本号
 *
 * Created by konghang on 2017/1/19.
 */
@Singleton
@Service
public class HelloWordServiceWithoutVersion implements HelloWorldService {

    @Override
    public String greet(String name) {
        System.out.println(String.format("%s say hello to me .", name));
        return String.format("Hi, %s . I am Jack. We are friends and version none .",name);
    }
}
