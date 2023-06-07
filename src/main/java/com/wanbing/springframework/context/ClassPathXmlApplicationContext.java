package com.wanbing.springframework.context;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.beans.factory.BeanFactory;
import com.wanbing.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.wanbing.springframework.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.wanbing.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.wanbing.springframework.beans.factory.config.BeanPostProcessor;
import com.wanbing.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.wanbing.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.wanbing.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.wanbing.springframework.core.ClassPathXmlResource;
import com.wanbing.springframework.core.Resource;

import java.util.ArrayList;
import java.util.List;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext{
    private DefaultListableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public ClassPathXmlApplicationContext(String fileName){
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
        Resource resource = new ClassPathXmlResource(fileName);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // 加载resource称为beanDefinition
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if(isRefresh){
            try {
                refresh();
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void initApplicationEventPublisher() {
        ApplicationEventPublisher publisher = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(publisher);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {

    }

    @Override
    public void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    public void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }
}
