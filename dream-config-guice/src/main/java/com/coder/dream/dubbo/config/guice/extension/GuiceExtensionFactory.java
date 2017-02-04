/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.coder.dream.dubbo.config.guice.extension;

import com.alibaba.dubbo.common.extension.ExtensionFactory;
import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

import java.util.Set;

/**
 * GuiceExtensionFactory
 * 
 * @author konghang
 */
public class GuiceExtensionFactory implements ExtensionFactory {
    
    private static final Set<Injector> injectors = new ConcurrentHashSet<Injector>();
    
    public static void addInjector(Injector context) {
        injectors.add(context);
    }

    public static void removeInjector(Injector context) {
        injectors.remove(context);
    }

    @SuppressWarnings("unchecked")
    public <T> T getExtension(Class<T> type, String name) {
        Key<T> key = StringUtils.isBlank(name) ? Key.get(type) : Key.get(type, Names.named(name));
        for (Injector injector : injectors) {
            if(injector.getExistingBinding(key) != null){
               return injector.getInstance(key);
            }
        }
        return null;
    }

}
