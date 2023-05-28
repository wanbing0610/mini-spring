package com.wanbing.springframework.beans.factory.config;

import com.wanbing.springframework.beans.exception.BeansException;

public interface BeanPostProcessor {
    /**
     *
     * @param bean  主对象的bean实例
     * @param beanName  被注入对象 bean 名称
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
