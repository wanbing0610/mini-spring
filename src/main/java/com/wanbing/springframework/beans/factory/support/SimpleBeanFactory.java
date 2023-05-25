package com.wanbing.springframework.beans.factory.support;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.beans.factory.config.BeanDefinition;
import com.wanbing.springframework.beans.factory.BeanFactory;
import com.wanbing.springframework.beans.factory.config.ConstructorArgumentValue;
import com.wanbing.springframework.beans.factory.config.ConstructorArgumentValues;
import com.wanbing.springframework.beans.factory.config.PropertyValue;
import com.wanbing.springframework.beans.factory.config.PropertyValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory底层容器
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    // 一级缓存，用于存放实例化完成, 初始化没有完成的bean
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    private List<String> beanDefinitionNames=new ArrayList<>();

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {

        // 尝试在容器中获取bean
        Object singleton = this.getSingleton(beanName);

        // 如果容器中获取不到bean,有可能是懒加载, 则创建Bean
        if(singleton == null){
            singleton = this.earlySingletonObjects.get(beanName);
            if( singleton == null){
                // 尝试获取BeanDefinition，通过它利用反射进行实例化
                BeanDefinition beanDefinition = beanDefinitions.get(beanName);
                if(beanDefinition == null){
                    throw new BeansException("no BeanDefinitions");
                }
                singleton=createBean(beanDefinition);
                this.registerBean(beanName, singleton);
            }
        }
         return singleton;
    }

    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    private Object createBean(BeanDefinition beanDefinition){
        Object obj = doCreateBean(beanDefinition);
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);

        Class<?> aClass = obj.getClass();
        handleProperties(beanDefinition, aClass ,obj);
        return obj;
    }


    private Object doCreateBean(BeanDefinition beanDefinition){
        // Bean的类信息
        Class<?> clz = null;

        try {
            // 核心代码1：获取字节码Class对象
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // 构造器生成对象
        ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();

        if( !constructorArgumentValues.isEmpty() ){
            Class<?>[] paramTypes = new Class<?>[constructorArgumentValues.getArgumentCount()];
            Object[] paramValues = new Object[constructorArgumentValues.getArgumentCount()];

            for(int i = 0; i < constructorArgumentValues.getArgumentCount(); i++){
                ConstructorArgumentValue argumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
                if("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType()) ){
                    paramTypes[i] = String.class;
                    paramValues[i] = argumentValue.getValue();
                }else if("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType() )){
                    paramTypes[i] = Integer.class;
                    paramValues[i] = Integer.valueOf((String)argumentValue.getValue());
                }else if("int".equals(argumentValue.getType())){
                    paramTypes[i] = int.class;
                    String stringValue = (String)argumentValue.getValue();
                    paramValues[i] = Integer.parseInt(stringValue);
                }
            }

            // Bean的实例
            Object obj;

            // 构造器信息
            Constructor con;
            try {
                // 核心代码2：获取字节码Class对象的有参构造器
                con = clz.getConstructor(paramTypes);
                // 通过构造器对象生成实例
                obj = con.newInstance(paramValues);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return obj;
        }else{
            // Bean的实例
            Object obj = null;
            try {
                obj = clz.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return obj;
        }
    }


    /**
     *
     * @param beanDefinition
     * @param clz bean的类信息
     * @param obj bean的值
     */
    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj){
        // 获取属性依赖
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if( !propertyValues.isEmpty() ){
            for(int i = 0; i< propertyValues.size(); i++){
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pName = propertyValue.getName();
                String pType = propertyValue.getType();
                Object pValue = propertyValue.getValue();
                // ture 引用类型，false 基本类型
                boolean isRef = propertyValue.getIsRef();


                // method = clz.getMethod(methodName, paramTypes);
                // method.invoke(obj, paramValues); obj是对象实例, paramValues
                Class<?>[] paramTypes = new Class[1];
                Object[] paramValues = new Object[1];

                // 如果是基本类型
                if( !isRef ){
                    if("String".equals(pType) || "java.lang.String".equals(pType)){
                        paramTypes[0] = String.class;
                    } else if ( "Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                    } else if ( "int".equals(pType) ) {
                        paramTypes[0] = int.class;
                    }else {
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = pValue;
                }else { // 如果是引用类型
                    try {
                        // set属性时,需要知道set的参数类型
                        paramTypes[0]  = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        //
                        paramValues[0]= getBean((String)pValue);
                    } catch (BeansException e) {
                        throw new RuntimeException(e);
                    }
                }

                String methodName = "set" + pName.substring(0,1).toUpperCase() + pName.substring(1);

                Method method;

                try {
                    method = clz.getMethod(methodName, paramTypes);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }

                try {
                    method.invoke(obj, paramValues);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }


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
