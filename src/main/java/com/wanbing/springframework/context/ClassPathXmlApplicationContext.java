package com.wanbing.springframework.context;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.beans.factory.BeanDefinition;
import com.wanbing.springframework.beans.factory.BeanFactory;
import com.wanbing.springframework.beans.factory.SimpleBeanFactory;
import com.wanbing.springframework.beans.factory.support.XmlBeanDefinitionReader;
import com.wanbing.springframework.core.ClassPathXmlResource;
import com.wanbing.springframework.core.Resource;

public class ClassPathXmlApplicationContext implements BeanFactory {
    private final BeanFactory beanFactory;
    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        this.beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
    }

    /**
     * 获取bean
     * @param beanName bean的id
     * @return 返回bean id 对应的实例
     * @throws BeansException bean异常
     */
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanFactory.registerBeanDefinition(beanDefinition);
    }

}
