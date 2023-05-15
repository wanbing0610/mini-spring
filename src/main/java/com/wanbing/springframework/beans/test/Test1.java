package com.wanbing.springframework.beans.test;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.context.ClassPathXmlApplicationContext;

public class Test1 {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/beans.xml");
        AService aservice = (AService) ctx.getBean("aservice");
        aservice.sayHello();

    }

}
