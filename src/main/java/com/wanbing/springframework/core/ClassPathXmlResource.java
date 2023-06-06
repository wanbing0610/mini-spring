package com.wanbing.springframework.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * xml资源
 */
public class ClassPathXmlResource implements Resource{
    private final Iterator<Element> elementIterator;

    public ClassPathXmlResource(String xmlPath) {
        SAXReader saxReader =  new SAXReader();
        System.out.println(xmlPath);
        System.out.println(this.getClass().getClassLoader().getResource(""));
        System.out.println(ClassPathXmlResource.class.getClassLoader().getResource(""));
        System.out.println(this.getClass().getClassLoader().getResource(xmlPath));

        URL resource = this.getClass().getClassLoader().getResource(xmlPath);
        try {
            Document document = saxReader.read(resource);
            Element rootElement = document.getRootElement();
            this.elementIterator = rootElement.elementIterator();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return this.elementIterator.next();
    }
}
