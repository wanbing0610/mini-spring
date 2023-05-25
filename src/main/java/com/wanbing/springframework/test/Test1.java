package com.wanbing.springframework.test;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.context.ClassPathXmlApplicationContext;

public class Test1 {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/beans.xml");
        AServiceImpl aService = (AServiceImpl) ctx.getBean("aservice");
//        aService.sayHello();
//        aService.getRef1().sayHello();
//        aService.getRef1().getBbs().say();
    }

}
