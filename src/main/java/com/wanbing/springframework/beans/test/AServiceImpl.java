package com.wanbing.springframework.beans.test;

public class AServiceImpl implements AService{
    @Override
    public void sayHello() {
        System.out.println("a service 1 say hello");
    }
}
