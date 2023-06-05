package com.wanbing.springframework.context;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.beans.factory.ListableFactory;
import com.wanbing.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.wanbing.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.wanbing.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.wanbing.springframework.core.env.Environment;
import com.wanbing.springframework.core.env.EnvironmentCapable;

public interface ApplicationContext extends EnvironmentCapable, ListableFactory, ConfigurableBeanFactory, ApplicationEventPublisher {
    String getApplicationName();
    long getStartupDate();
    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
    void setEnvironment(Environment environment);
    Environment getEnvironment();
    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);
    void refresh() throws BeansException, IllegalStateException;
    void close();
    boolean isActive();
}
