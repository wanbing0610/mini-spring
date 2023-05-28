package com.wanbing.springframework.beans.factory.annotation;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.wanbing.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if( fields != null && fields.length > 0){
            for( Field field : fields ){
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if( isAutowired ){
                    String fieldName = field.getName();
                    // 被注入的bean
                    Object autowiredObj = this.getBeanFactory().getBean(fieldName);

                    field.setAccessible(true);
                    try {
                        field.set(bean, autowiredObj);
                        System.out.println("autowire " + fieldName + " for bean " + beanName);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        return bean;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
