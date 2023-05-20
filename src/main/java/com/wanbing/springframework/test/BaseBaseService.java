package com.wanbing.springframework.test;

public class BaseBaseService{
    private AServiceImpl as;

    public AService getAs() {
        return as;
    }

    public void setAs(AServiceImpl as) {
        this.as = as;
    }

    public void say(){
        System.out.println("Base base Service say hello");
    }


}
