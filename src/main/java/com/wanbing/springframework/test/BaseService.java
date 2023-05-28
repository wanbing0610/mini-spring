package com.wanbing.springframework.test;

import com.wanbing.springframework.beans.factory.annotation.Autowired;

public class BaseService {
    @Autowired
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
