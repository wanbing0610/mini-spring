package com.wanbing.springframework.beans.test;

public class AServiceImpl implements AService{
    private String property1;

    private String name;

    private Integer level;

    public AServiceImpl() {
    }

    public AServiceImpl(String name, Integer level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public void sayHello() {
        System.out.println("a service 1 say hello");
    }
}
