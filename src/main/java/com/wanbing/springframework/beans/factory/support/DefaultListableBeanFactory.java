package com.wanbing.springframework.beans.factory.support;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.wanbing.springframework.beans.factory.config.BeanDefinition;
import com.wanbing.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return (String[]) this.beanDefinitionNames.toArray();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        Set<Map.Entry<String, BeanDefinition>> entries = this.beanDefinitionMap.entrySet();
        List<String> collect = entries.stream()
                .filter(entry -> type.isAssignableFrom(entry.getValue().getClass()))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
        return collect.toArray(new String[collect.size()]);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);

       Map<String, T> result = new LinkedHashMap<>();
       for(String beanName : beanNames ){
           Object bean = getBean(beanName);
           result.put(beanName, (T)bean);
       }

        return result;
    }


}
