package com.coder.dream.dubbo.config.guice.scanner;

import java.util.Set;

/**
 * Created by konghang on 2016/12/15.
 */
public class ScanAnnotations {

    //服务类
    private Set<Class<?>> serviceClasses;

    public Set<Class<?>> getServiceClasses() {
        return serviceClasses;
    }

    public void setServiceClasses(Set<Class<?>> serviceClasses) {
        this.serviceClasses = serviceClasses;
    }
}
