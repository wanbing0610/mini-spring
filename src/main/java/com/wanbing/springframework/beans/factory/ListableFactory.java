package com.wanbing.springframework.beans.factory;


import com.wanbing.springframework.beans.exception.BeansException;

import java.util.Map;

public interface ListableFactory extends BeanFactory{
    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

}
