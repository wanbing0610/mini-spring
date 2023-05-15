package com.wanbing.springframework.beans.factory;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.beans.factory.config.ArgumentValue;
import com.wanbing.springframework.beans.factory.config.ArgumentValues;
import com.wanbing.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.wanbing.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory底层容器
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private final Map<String,BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();
    private List<String> beanDefinitionNames=new ArrayList<>();

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 尝试在容器中获取bean
        Object singleton = this.singletons.get(beanName);
        // 如果容器中获取不到bean,有可能是懒加载, 这是获取的时候才创建Bean
        if(singleton == null){
                // 尝试获取BeanDefinition，通过它利用反射进行实例化
                BeanDefinition beanDefinition = beanDefinitions.get(beanName);
                if(beanDefinition == null){
                    throw new BeansException("no BeanDefinitions");
                }
            try {
                Object newSingleton = Class.forName(beanDefinition.getClassName()).newInstance();
                this.singletons.put(beanName, newSingleton);
                return newSingleton;
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
         return singleton;
    }

//    private Object createBean(BeanDefinition beanDefinition){
//        // 类信息
//        Class<?> clz = null;
//        Object obj = null;
//        // 构造器信息
//        Constructor constructor = null;
//
//        try {
//            clz = Class.forName(beanDefinition.getClassName());
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//
//
//        // 处理依赖
//        ArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
//        if( !argumentValues.isEmpty() ){
//            Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
//            Object[] paramValues = new Object[argumentValues.getArgumentCount()];
//        }else{
//
//        }
//
//        return null;
//    }


    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }

    @Override
    public boolean containsBean(String name) {
        return this.containsSingleton(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitions.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitions.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitions.get(name).getClass();
    }


    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitions.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);

        // 这段代码是用来做什么的
        if( !beanDefinition.isLazyInit()){
            try {
                getBean(name);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        // 删除beanDefinition本身
        this.beanDefinitions.remove(name);
        // 删除beanDefinitions的名称
        this.beanNames.remove(name);
        // beanDefinitions删除，他对应的实例子应该删除
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitions.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitions.containsKey(name);
    }



}
