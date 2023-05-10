package com.wanbing.springframework.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * 定义路径下的资源
 */
public class ClassPathXmlResource implements Resource{
    private final Iterator<Element> elementIterator;

    public ClassPathXmlResource(String xmlPath) {
        SAXReader saxReader =  new SAXReader();
        URL resource = this.getClass().getResource(xmlPath);
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
