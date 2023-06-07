package com.wanbing.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    HandlerMethod getHandler(HttpServletRequest request);

}
