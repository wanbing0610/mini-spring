package com.wanbing.springframework.web.servlet;

import com.wanbing.springframework.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestMappingHandlerAdapter implements HandlerAdapter{
    WebApplicationContext wac;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        handleInternal(request, response, (HandlerMethod) handler);
    }


    private void handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod){
        Method method = handlerMethod.getMethod();
        Object obj = handlerMethod.getBean();
        Object objResult;
        try {
            objResult = method.invoke(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        try {
            response.getWriter().append(objResult.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
