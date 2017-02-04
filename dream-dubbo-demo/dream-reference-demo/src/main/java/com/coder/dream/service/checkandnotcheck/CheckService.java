package com.coder.dream.service.checkandnotcheck;

import com.alibaba.dubbo.config.annotation.Reference;
import com.coder.dream.guice.lifecycle.Start;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/1/19.
 */
@Singleton
public class CheckService {

    @Reference
    private RefereceCheckService refereceCheckService;

    public void test() {
        System.out.println("this is check service : " + refereceCheckService.check());
    }
}
