package com.coder.dream.service.checkandnotcheck;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/1/19.
 */
@Singleton
public class OnlyNotCheckEntrace {

    @Inject
    public OnlyNotCheckEntrace(NotCheckService notCheckService) {
        notCheckService.test();
    }
}
