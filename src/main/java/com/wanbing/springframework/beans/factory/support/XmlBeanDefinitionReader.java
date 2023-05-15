package com.wanbing.springframework.beans.factory.support;

import com.wanbing.springframework.beans.factory.BeanDefinition;
import com.wanbing.springframework.beans.factory.BeanFactory;
import com.wanbing.springframework.beans.factory.SimpleBeanFactory;
import com.wanbing.springframework.core.Resource;
import org.dom4j.Element;

/**
 * 加载BeanDefinition的能力
 */
public class XmlBeanDefinitionReader {
    private final SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource){
        while (resource.hasNext()){
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            this.simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }

}
