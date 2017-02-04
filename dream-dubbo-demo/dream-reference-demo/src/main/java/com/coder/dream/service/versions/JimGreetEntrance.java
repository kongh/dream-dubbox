package com.coder.dream.service.versions;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by konghang on 2017/1/19.
 */
@Singleton
public class JimGreetEntrance {

    @Inject
    public JimGreetEntrance(JimGreetService jimGreetService) {
        jimGreetService.say();
    }
}
