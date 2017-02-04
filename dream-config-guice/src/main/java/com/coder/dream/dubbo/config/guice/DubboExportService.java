package com.coder.dream.dubbo.config.guice;

import com.alibaba.dubbo.common.extension.ExtensionFactory;
import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Dubbo暴露服务
 *
 * Created by konghang on 2017/1/3.
 */
public class DubboExportService implements ExtensionFactorySetter {

    private ExtensionFactory extensionFactory;

    private Injector injector;

    private final Set<ServiceConfig<?>> serviceConfigs = new ConcurrentHashSet<ServiceConfig<?>>();

    @Inject
    public DubboExportService(Injector injector) {
        this.injector = injector;
    }

    public void doExportServices(Set<Class<?>> serviceClasses) {
        for(Class<?> serviceClass : serviceClasses){
            Service service = serviceClass.getAnnotation(Service.class);
            ServiceConfig serviceConfig = new ServiceConfig(service);
            if (void.class.equals(service.interfaceClass())
                    && "".equals(service.interfaceName())) {
                if (serviceClass.getInterfaces().length > 0) {
                    serviceConfig.setInterface(serviceClass.getInterfaces()[0]);
                } else {
                    throw new IllegalStateException("Failed to export remote service class " + serviceClass.getName() + ", cause: The @Service undefined interfaceClass or interfaceName, and the service class unimplemented any interfaces.");
                }
            }
            if (service.registry() != null && service.registry().length > 0) {
                List<RegistryConfig> registryConfigs = new ArrayList<RegistryConfig>();
                for (String registryId : service.registry()) {
                    if (registryId != null && registryId.length() > 0) {
                        RegistryConfig registryConfig = extensionFactory.getExtension(RegistryConfig.class, registryId);
                        if(registryConfig != null){
                            registryConfigs.add(registryConfig);
                        }
                    }
                }
                if(registryConfigs.size() != 0){
                    serviceConfig.setRegistries(registryConfigs);
                }
            }
            if (service.provider() != null && service.provider().length() > 0) {
                ProviderConfig providerConfig = extensionFactory.getExtension(ProviderConfig.class, service.provider());
                if(providerConfig != null){
                    serviceConfig.setProvider(providerConfig);
                }
            }
            if (service.monitor() != null && service.monitor().length() > 0) {
                MonitorConfig monitorConfig = extensionFactory.getExtension(MonitorConfig.class, service.monitor());
                if(monitorConfig != null){
                    serviceConfig.setMonitor(monitorConfig);
                }
            }
            if(service.application() != null && service.application().length() > 0){
                ApplicationConfig applicationConfig = extensionFactory.getExtension(ApplicationConfig.class, service.application());
                if(applicationConfig != null){
                    serviceConfig.setApplication(applicationConfig);
                }
            }
            if (service.module() != null && service.module().length() > 0) {
                ModuleConfig moduleConfig = extensionFactory.getExtension(ModuleConfig.class, service.module());
                if(moduleConfig != null){
                    serviceConfig.setModule(moduleConfig);
                }
            }
            if (service.provider() != null && service.provider().length() > 0) {
                ProviderConfig providerConfig = extensionFactory.getExtension(ProviderConfig.class, service.provider());
                if(providerConfig != null){
                    serviceConfig.setProvider(providerConfig);
                }
            } else {

            }
            if (service.protocol() != null && service.protocol().length > 0) {
                List<ProtocolConfig> protocolConfigs = new ArrayList<ProtocolConfig>();
                for (String protocolId : service.protocol()) {
                    if (protocolId != null && protocolId.length() > 0) {
                        ProtocolConfig protocolConfig = extensionFactory.getExtension(ProtocolConfig.class, protocolId);
                        protocolConfigs.add(protocolConfig);
                    }
                }
                if(protocolConfigs.size() != 0){
                    serviceConfig.setProtocols(protocolConfigs);
                }
            }
            serviceConfig.setDelay(service.delay());
            serviceConfig.setRef(injector.getInstance(serviceClass));
            serviceConfigs.add(serviceConfig);
            serviceConfig.export();
        }
    }

    public Set<ServiceConfig<?>> getServiceConfigs() {
        return serviceConfigs;
    }

    @Override
    public void setExtensionFactory(ExtensionFactory extensionFactory) {
        this.extensionFactory = extensionFactory;
    }
}
