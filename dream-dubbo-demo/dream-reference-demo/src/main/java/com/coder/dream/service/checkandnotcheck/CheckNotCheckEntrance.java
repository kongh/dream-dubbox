package com.coder.dream.service.checkandnotcheck;

import com.coder.dream.guice.lifecycle.Start;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/1/19.
 */
@Singleton
public class CheckNotCheckEntrance {

    private CheckService checkService;

    private NotCheckService notCheckService;

    @Inject
    public CheckNotCheckEntrance(CheckService checkService, NotCheckService notCheckService) {
        this.checkService = checkService;
        this.notCheckService = notCheckService;
    }

    @Start
    public void test(){
        try {
            checkService.test();
            notCheckService.test();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
