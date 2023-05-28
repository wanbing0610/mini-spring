package com.wanbing.springframework.context;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.beans.factory.BeanFactory;
import com.wanbing.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.wanbing.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.wanbing.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.wanbing.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.wanbing.springframework.core.ClassPathXmlResource;
import com.wanbing.springframework.core.Resource;

import java.util.ArrayList;
import java.util.List;

public class ClassPathXmlApplicationContext implements BeanFactory , ApplicationEventPublisher{
    private final AutowireCapableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors =
            new ArrayList<BeanFactoryPostProcessor>();

    public ClassPathXmlApplicationContext(String fileName){
        this(fileName,true);
    }
    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        AutowireCapableBeanFactory autowireCapableBeanFactory = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(autowireCapableBeanFactory);
        this.beanFactory = autowireCapableBeanFactory;
        // 负责解析BeanDefinition
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
        if(isRefresh){
            try {
                refresh();
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
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


    public List getBeanFactoryPostProcessors() { return this.beanFactoryPostProcessors; }

    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) { this.beanFactoryPostProcessors.add(postProcessor); }

    public void refresh() throws BeansException, IllegalStateException {
        // Register bean processors that intercept bean creation.
        registerBeanPostProcessors(this.beanFactory); // Initialize other special beans in specific context subclasses.
        onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory bf) {
        //if (supportAutowire) {
        bf.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
        //}
    }

    private void onRefresh() {
        this.beanFactory.refresh();
    }




}
