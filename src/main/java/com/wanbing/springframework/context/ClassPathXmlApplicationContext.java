package com.wanbing.springframework.context;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.beans.factory.BeanFactory;
import com.wanbing.springframework.beans.factory.SimpleBeanFactory;
import com.wanbing.springframework.beans.factory.support.XmlBeanDefinitionReader;
import com.wanbing.springframework.core.ClassPathXmlResource;
import com.wanbing.springframework.core.Resource;

public class ClassPathXmlApplicationContext implements BeanFactory , ApplicationEventPublisher{
    private final BeanFactory beanFactory;
    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory simpleBeanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(simpleBeanFactory);
        this.beanFactory = simpleBeanFactory;
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
    public boolean containsBean(String name) {
        return this.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return this.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return this.getType(name);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }

//    @Override
//    public void registerBean(String beanName, Object obj) {
//        this.registerBean(beanName, obj);
//    }


}
