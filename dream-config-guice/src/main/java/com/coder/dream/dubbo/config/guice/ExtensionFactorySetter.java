package com.coder.dream.dubbo.config.guice;

import com.alibaba.dubbo.common.extension.ExtensionFactory;

/**
 * Created by konghang on 2017/1/3.
 */
public interface ExtensionFactorySetter {

    /**
     * 设置
     *
     * @param extensionFactory
     */
    void setExtensionFactory(ExtensionFactory extensionFactory);
}
