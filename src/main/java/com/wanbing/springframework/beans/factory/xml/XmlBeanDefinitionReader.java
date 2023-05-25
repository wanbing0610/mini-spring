package com.wanbing.springframework.beans.factory.xml;

import com.wanbing.springframework.beans.factory.config.BeanDefinition;
import com.wanbing.springframework.beans.factory.support.AbstractBeanFactory;
import com.wanbing.springframework.beans.factory.config.ConstructorArgumentValue;
import com.wanbing.springframework.beans.factory.config.ConstructorArgumentValues;
import com.wanbing.springframework.beans.factory.config.PropertyValue;
import com.wanbing.springframework.beans.factory.config.PropertyValues;
import com.wanbing.springframework.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 把xmlResource, 解析成beanDefinition, 并调用Factory的注册api
 */
public class XmlBeanDefinitionReader {
    private final AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
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

            if( constructorElements != null && !constructorElements.isEmpty()){
                ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
                constructorElements.stream().forEach(property -> {
                    String pType = property.attributeValue("type");
                    String pName = property.attributeValue("name");
                    String pValue = property.attributeValue("value");
                    constructorArgumentValues.addArgumentValue(new ConstructorArgumentValue(pType, pName, pValue));
                });
                beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
            }




            // 读取bean的属性
            List<Element> propertyElements = element.elements("property");
            if( propertyElements != null && !propertyElements.isEmpty()){
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
            }

            this.beanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }

}
