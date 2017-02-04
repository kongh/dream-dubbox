package com.coder.dream.service.checkandnotcheck;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/1/19.
 */
@Singleton
public class CheckNotCheckEntrance {

    @Inject
    public CheckNotCheckEntrance(CheckService checkService, NotCheckService notCheckService) {
        checkService.test();
        notCheckService.test();
    }
}
