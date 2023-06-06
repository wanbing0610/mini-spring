package com.wanbing.springframework.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 上下文监听器，然后用于构建一个WebApplicationContext
 */
public class ContextLoaderListener implements ServletContextListener {
    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

    private WebApplicationContext context;

    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext webApplicationContext) {
        this.context = webApplicationContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initWebApplicationContext(servletContextEvent.getServletContext());
    }

    private void initWebApplicationContext(ServletContext servletContext){
        String contextLocation = servletContext.getInitParameter(this.CONFIG_LOCATION_PARAM);
        AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext(contextLocation);
        wac.setServletContext(servletContext);
        this.context = wac;
        // servlet和spring上下文环境绑定起来
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
