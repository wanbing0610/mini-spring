package com.wanbing.springframework.test.controller;

import com.wanbing.springframework.web.RequestMapping;


public class HelloWorldController {
    @RequestMapping(value = "/test/hello")
    public String doGet(){
        return "hello world";
    }

}
