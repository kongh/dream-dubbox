package com.coder.dream.dubbo.config.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;

/**
 * Created by konghang on 2016/12/15.
 */
public abstract class DubboModule extends AbstractModule{

    private String scanPackages = "";

    public DubboModule(String scanPackages) {
        this.scanPackages = scanPackages;
    }

    @Override
    protected void configure() {
        //暴露服务
        bindConstant().annotatedWith(Names.named("dubbo.scan.packages")).to(scanPackages);
        bind(DubboExportService.class).in(Scopes.SINGLETON);

        //注解启动
        bind(DefaultDubboAnnotationService.class).asEagerSingleton();
        bind(DubboAnnotationService.class).to(DefaultDubboAnnotationService.class);

        //引用服务
        DubboReferenceService referenceService = new DubboReferenceService();
        referenceService.setScanPackages(scanPackages);
        bind(DubboReferenceService.class).toInstance(referenceService);
        bindListener(Matchers.any(), referenceService);
    }
}
