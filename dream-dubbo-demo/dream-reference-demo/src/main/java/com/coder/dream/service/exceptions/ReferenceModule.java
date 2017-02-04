package com.coder.dream.service.exceptions;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.coder.dream.dubbo.config.guice.DubboModule;
import com.google.inject.Provides;

/**
 * Created by konghang on 2017/1/3.
 */
public class ReferenceModule extends DubboModule {

    public static final String REGISTRY_ADDRESS = "zookeeper://127.0.0.1:2181";

    public ReferenceModule() {
        super("com.coder.dream.service.exceptions");
    }

    public ReferenceModule(String scanPackages) {
        super(scanPackages);
    }

    @Override
    protected void configure() {
        super.configure();
        bind(ExceptionEntrance.class).asEagerSingleton();
    }

    @Provides
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-konghang-com.coder.dream.reference-test-app");
        return applicationConfig;
    }

    @Provides
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(REGISTRY_ADDRESS);
        return registryConfig;
    }

    @Provides
    public ProtocolConfig protocolConfig() {
        ProtocolConfig registryConfig = new ProtocolConfig();
        registryConfig.setName("dubbo");
        registryConfig.setPort(20880);
        return registryConfig;
    }
}
