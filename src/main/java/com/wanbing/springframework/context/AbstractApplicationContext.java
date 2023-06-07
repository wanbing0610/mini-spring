package com.wanbing.springframework.context;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.wanbing.springframework.beans.factory.config.BeanPostProcessor;
import com.wanbing.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.wanbing.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractApplicationContext implements ApplicationContext{
    /**
     * 环境
     */
    private Environment environment;

    /**
     * 工厂处理器
     */
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    /**
     * 事件发布者
     */
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 启动时间
     */
    private long startupDate;

    private final AtomicBoolean active = new AtomicBoolean();

    private final AtomicBoolean closed = new AtomicBoolean();

    public abstract void registerListeners();
    public abstract void initApplicationEventPublisher();
    public abstract void postProcessBeanFactory(ConfigurableListableBeanFactory bf);
    public abstract void registerBeanPostProcessors(ConfigurableListableBeanFactory bf);
    public abstract void onRefresh();
    public abstract void finishRefresh();


    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return getBeanFactory().isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return getBeanFactory().isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return getBeanFactory().getType(name);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        getBeanFactory().addBeanPostProcessor(beanPostProcessor);
    }

    @Override
    public int getBeanPostProcessorCount() {
        return getBeanFactory().getBeanPostProcessorCount();
    }

    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {
        getBeanFactory().registerDependentBean(beanName, dependentBeanName);
    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return getBeanFactory().getDependentBeans(beanName);
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return getBeanFactory().getDependenciesForBean(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        getBeanFactory().registerSingleton(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return getBeanFactory().getSingleton(beanName);
    }

    @Override
    public Boolean containsSingleton(String beanName) {
        return getBeanFactory().containsSingleton(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return getBeanFactory().getSingletonNames();
    }

    @Override
    public String getApplicationName() {
        return "null";
    }

    @Override
    public long getStartupDate() {
        return this.startupDate;
    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        //1.
        postProcessBeanFactory(getBeanFactory());
        //2.注册工厂处理器
        registerBeanPostProcessors(getBeanFactory());
        //3.初始化应用事件发布者
        initApplicationEventPublisher();
        //4.上下文刷新
        onRefresh();
        //5.注册监听者
        registerListeners();
        //6.完成刷新
        finishRefresh();
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isActive() {
        return false;
    }


    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
