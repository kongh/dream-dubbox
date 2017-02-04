package com.coder.dream.service.checkandnotcheck;

import com.coder.dream.guice.lifecycle.Start;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/1/19.
 */
@Singleton
public class OnlyNotCheckEntrace {

    private NotCheckService notCheckService;

    @Inject
    public OnlyNotCheckEntrace(NotCheckService notCheckService) {
        this.notCheckService = notCheckService;
    }

    @Start
    public void test(){
        notCheckService.test();
    }
}
