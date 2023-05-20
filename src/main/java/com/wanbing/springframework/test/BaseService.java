package com.wanbing.springframework.test;

public class BaseService {
    private BaseBaseService bbs;

    public BaseBaseService getBbs() {
        return bbs;
    }

    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }

    public void sayHello(){
        System.out.println("BaseService say hello");
    }


}
