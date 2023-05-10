package com.wanbing.springframework.beans.factory;

import com.wanbing.springframework.beans.exception.BeansException;

public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    void registerBeanDefinition(BeanDefinition beanDefinition);
}
