package com.wanbing.springframework.beans.factory.config;

/**
 * 管理单例的bean
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    Boolean containsSingleton(String beanName);

    String[] getSingletonNames();
}
