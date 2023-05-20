package com.wanbing.springframework.beans.factory.support;

import com.sun.xml.internal.ws.util.StringUtils;
import com.wanbing.springframework.beans.factory.BeanDefinition;
import com.wanbing.springframework.beans.factory.SimpleBeanFactory;
import com.wanbing.springframework.beans.factory.config.ArgumentValue;
import com.wanbing.springframework.beans.factory.config.ArgumentValues;
import com.wanbing.springframework.beans.factory.config.PropertyValue;
import com.wanbing.springframework.beans.factory.config.PropertyValues;
import com.wanbing.springframework.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

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

            // 读取xml中bean的属性
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            // 读取bean的构造器
            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues argumentValues = new ArgumentValues();
            constructorElements.stream().forEach(property -> {
                String pType = property.attributeValue("type");
                String pName = property.attributeValue("name");
                String pValue = property.attributeValue("value");
                argumentValues.addArgumentValue(new ArgumentValue(pType, pName, pValue));
            });
            beanDefinition.setConstructorArgumentValues(argumentValues);


            // 读取bean的属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues propertyValues = new PropertyValues();
            List<String> refs = new ArrayList<>();
            propertyElements.stream().forEach(property -> {
                String pType = property.attributeValue("type");
                String pName = property.attributeValue("name");
                String pValue = property.attributeValue("value");
                String pRef = property.attributeValue("ref");

                // 如果是基本类型,pv则代表值，如果是引用类型，pv则代表bean的id
                String pV = "";
                boolean isRef = false;
                // 如果xml的value属性不为空，则表示是基本类型
                if(pValue != null && !pValue.isEmpty() ){
                    pV = pValue;
                    isRef = false;
                }else if(pRef != null && !pRef.isEmpty()){ // 引用类型
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                propertyValues.addPropertyValue(new PropertyValue(pType, pName, pV, isRef));
            });
            beanDefinition.setPropertyValues(propertyValues);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);


            this.simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }

}
