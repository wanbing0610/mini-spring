package com.wanbing.springframework.beans.factory.support;

import com.wanbing.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储单例bean
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    protected List<String> beanNames = new ArrayList<>();

    protected Map<String,Object> singletons = new ConcurrentHashMap<>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized(this.singletons){
            this.singletons.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public Boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[])this.beanNames.toArray();
    }

    protected void removeSingleton(String beanName){
        synchronized (this.singletons){
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }
}
