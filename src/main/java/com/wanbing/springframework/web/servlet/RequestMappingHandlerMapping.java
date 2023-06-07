package com.wanbing.springframework.web.servlet;

import com.wanbing.springframework.beans.exception.BeansException;
import com.wanbing.springframework.web.RequestMapping;
import com.wanbing.springframework.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class RequestMappingHandlerMapping implements HandlerMapping{
    private WebApplicationContext wac;

    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext wac) {
        this.wac = wac;
        initMapping();
    }


    protected void initMapping(){
        Class<?> clz;
        Object obj;

        String[] controllerNames = wac.getBeanDefinitionNames();
        for(String controllerName : controllerNames){
            try {
                clz = Class.forName(controllerName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                obj = wac.getBean(controllerName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }

            Method[] methods = clz.getDeclaredMethods();
            if(methods != null){
                for(Method method: methods){
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if( isRequestMapping ){
                        //
                        String methodName = method.getName();
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();
                        this.mappingRegistry.getUrlMappingNames().add(urlMapping);
                        this.mappingRegistry.getMappingObjs().put(urlMapping, obj);
                        this.mappingRegistry.getMappingMethods().put(urlMapping, method);
                    }
                }
            }
        }

        return;
    }


    @Override
    public HandlerMethod getHandler(HttpServletRequest request){
        String url = request.getServletPath();
        if( !this.mappingRegistry.getUrlMappingNames().contains(url)){
            return null;
        }
        Method method = this.mappingRegistry.getMappingMethods().get(url);
        Object obj = this.mappingRegistry.getMappingObjs().get(url);
        HandlerMethod handlerMethod = new HandlerMethod(method, obj);
        return handlerMethod;
    }
}
