package com.coder.dream.service.checkandnotcheck;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/1/19.
 */
@Singleton
@Service
public class RefereceCheckServiceWithVersionNone implements RefereceCheckService{

    @Override
    public String check() {
        return "this is reference check = true demo";
    }
}
