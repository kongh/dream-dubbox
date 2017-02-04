package com.coder.dream.service.checkandnotcheck;

import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/1/19.
 */
@Singleton
//@Service(version = "1") //this is not export service, demo for not check demo
public class RefereceNotCheckServiceWithVersion implements RefereceNotCheckService{

    @Override
    public String notcheck() {
        return "I am reference check = false demo with version 1";
    }
}
