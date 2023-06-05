package com.wanbing.springframework.beans.factory.support;

import com.wanbing.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储单例bean
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    protected List<String> beanNames = new ArrayList<>();

    protected Map<String,Object> singletons = new ConcurrentHashMap<>();

    /**
     * 依赖 bean 名称之间的映射：
     * bean 名称到一组依赖 bean 名称。
     */
    protected Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);
    /**
     *
     */
    protected Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

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

    /**
     * 为给定的bean，注册一个依赖的bean
     * @param beanName 给定bean的名称
     * @param dependentBeanName 依赖bean的名称
     */
    public void registerDependentBean(String beanName, String dependentBeanName) {
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);

        // 如果依赖的映射的set存在且包含了注册的Bean，则直接返回
        if (dependentBeans != null && dependentBeans.contains(dependentBeanName)) {
            return;
        }

        synchronized (this.dependentBeanMap) {
            dependentBeans = this.dependentBeanMap.get(beanName);
            // 如若依赖的映射的set不存在，则创建set，且放入dependentBeanName
            if (dependentBeans == null) {
                dependentBeans = new LinkedHashSet<String>(8);
                this.dependentBeanMap.put(beanName, dependentBeans);
            }
            dependentBeans.add(dependentBeanName);
        }

        synchronized (this.dependenciesForBeanMap) {
            Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(dependentBeanName);
            if (dependenciesForBean == null) {
                dependenciesForBean = new LinkedHashSet<String>(8);
                this.dependenciesForBeanMap.put(dependentBeanName, dependenciesForBean);
            }
            dependenciesForBean.add(beanName);
        }
    }

    /**
     * 获取依赖的bean
     * @param beanName
     * @return
     */
    public String[] getDependentBeans(String beanName) {
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
        if(dependentBeans != null ){
            return new String[0];
        }
        return dependentBeans.toArray( new String[dependentBeans.size()] );
    }


    /**
     * 获取依赖我的bean
     * @param beanName
     * @return
     */
    public String[] getDependenciesForBean(String beanName) {
        Set<String> dependenciesForBeanMap = this.dependenciesForBeanMap.get(beanName);
        if(dependenciesForBeanMap != null ){
            return new String[0];
        }
        return dependenciesForBeanMap.toArray( new String[dependenciesForBeanMap.size()] );
    }

}
