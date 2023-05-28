package com.wanbing.springframework.beans.factory.config;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.beans.factory.BeanFactory;

public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
