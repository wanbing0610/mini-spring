package com.wanbing.springframework.web;

import com.wanbing.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * 包裹spring上下文环境和servlet上下文环境
 */
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";
    ServletContext getServletContext();
    void setServletContext(ServletContext servletContext);
}
