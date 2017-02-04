package com.coder.dream.dubbo.config.guice;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.ExtensionFactory;
import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.inject.Binding;
import com.google.inject.spi.ProvisionListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Dubbo引用服务
 *
 * Created by konghang on 2017/1/3.
 */
public class DubboReferenceService implements ProvisionListener , ExtensionFactorySetter {

    private ExtensionFactory extensionFactory;

    private final ConcurrentMap<String, ReferenceConfig<?>> referenceConfigs = new ConcurrentHashMap<String, ReferenceConfig<?>>();

    private String[] scanPackages;

    private Object refer(Reference reference, Class<?> referenceClass) {
        String interfaceName;
        if (! "".equals(reference.interfaceName())) {
            interfaceName = reference.interfaceName();
        } else if (! void.class.equals(reference.interfaceClass())) {
            interfaceName = reference.interfaceClass().getName();
        } else if (referenceClass.isInterface()) {
            interfaceName = referenceClass.getName();
        } else {
            throw new IllegalStateException("The @Reference undefined interfaceClass or interfaceName, and the property type " + referenceClass.getName() + " is not a interface.");
        }
        String key = reference.group() + "/" + interfaceName + ":" + reference.version();
        ReferenceConfig<?> referenceConfig = referenceConfigs.get(key);
        if (referenceConfig == null) {
            referenceConfig = new ReferenceConfig<Object>(reference);
            if (void.class.equals(reference.interfaceClass())
                    && "".equals(reference.interfaceName())
                    && referenceClass.isInterface()) {
                referenceConfig.setInterface(referenceClass);
            }

            if (reference.registry() != null && reference.registry().length > 0) {
                List<RegistryConfig> registryConfigs = new ArrayList<RegistryConfig>();
                for (String registryId : reference.registry()) {
                    if (registryId != null && registryId.length() > 0) {
                        RegistryConfig registryConfig = extensionFactory.getExtension(RegistryConfig.class, registryId);
                        if(referenceConfig != null){
                            registryConfigs.add(registryConfig);
                        }
                    }
                }
                if(registryConfigs.size() != 0){
                    referenceConfig.setRegistries(registryConfigs);
                }
            }
            if (reference.consumer() != null && reference.consumer().length() > 0) {
                ConsumerConfig consumerConfig = extensionFactory.getExtension(ConsumerConfig.class, reference.consumer());
                if(consumerConfig != null){
                    referenceConfig.setConsumer(consumerConfig);
                }
            }
            if (reference.monitor() != null && reference.monitor().length() > 0) {
                MonitorConfig monitorConfig = extensionFactory.getExtension(MonitorConfig.class, reference.monitor());
                if(monitorConfig != null){
                    referenceConfig.setMonitor(monitorConfig);
                }
            }
            if (reference.application() != null && reference.application().length() > 0) {
                ApplicationConfig applicationConfig = extensionFactory.getExtension(ApplicationConfig.class, reference.application());
                if(applicationConfig != null){
                    referenceConfig.setApplication(applicationConfig);
                }
            }
            if (reference.module() != null && reference.module().length() > 0) {
                ModuleConfig moduleConfig = extensionFactory.getExtension(ModuleConfig.class, reference.module());
                if(moduleConfig != null){
                    referenceConfig.setModule(moduleConfig);
                }
            }
            if (reference.consumer() != null && reference.consumer().length() > 0) {
                ConsumerConfig consumerConfig = extensionFactory.getExtension(ConsumerConfig.class, reference.consumer());
                if(consumerConfig != null){
                    referenceConfig.setConsumer(consumerConfig);
                }
            }

            referenceConfigs.putIfAbsent(key, referenceConfig);
            referenceConfig = referenceConfigs.get(key);
        }
        return referenceConfig.get();
    }

    @Override
    public <T> void onProvision(ProvisionInvocation<T> provision) {
        Binding<T> binding = provision.getBinding();
        Class<? super T> clazz = binding.getKey().getTypeLiteral().getRawType();
        T bean = provision.provision();
        if (! isMatchPackage(bean)) {
            return ;
        }

        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.length() > 3 && name.startsWith("set")
                    && method.getParameterTypes().length == 1
                    && Modifier.isPublic(method.getModifiers())
                    && ! Modifier.isStatic(method.getModifiers())) {
                try {
                    Reference reference = method.getAnnotation(Reference.class);
                    if (reference != null) {
                        Object value = refer(reference, method.getParameterTypes()[0]);
                        if (value != null) {
                            method.invoke(bean, new Object[] { value });
                        }
                    }
                } catch (Exception e) {
                    throw new IllegalStateException("Failed to init remote service reference at method " + name + " in class " + bean.getClass().getName(), e);
                }
            }
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (! field.isAccessible()) {
                    field.setAccessible(true);
                }
                Reference reference = field.getAnnotation(Reference.class);
                if (reference != null) {
                    Object value = refer(reference, field.getType());
                    if (value != null) {
                        field.set(bean, value);
                    }
                }
            } catch (Exception e) {
                throw new IllegalStateException("Failed to init remote service reference at filed " + field.getName() + " in class " + bean.getClass().getName(), e);
            }
        }
    }

    public ConcurrentMap<String, ReferenceConfig<?>> getReferenceConfigs() {
        return referenceConfigs;
    }

    public void setScanPackages(String scanPackages) {
        this.scanPackages = (scanPackages == null || scanPackages.length() == 0) ? null
                : Constants.COMMA_SPLIT_PATTERN.split(scanPackages);
    }

    private boolean isMatchPackage(Object bean) {
        if (scanPackages == null || scanPackages.length == 0) {
            return false;
        }
        Class clazz = bean.getClass();

        String beanClassName = clazz.getName();
        for (String pkg : scanPackages) {
            if (beanClassName.startsWith(pkg)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setExtensionFactory(ExtensionFactory extensionFactory) {
        this.extensionFactory = extensionFactory;
    }

    public static void main(String[] args) {
        System.out.println(Constants.COMMA_SPLIT_PATTERN.split("com.a"));
    }
}
