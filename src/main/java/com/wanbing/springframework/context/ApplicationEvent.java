package com.wanbing.springframework.context;

import java.util.EventObject;

/**
 * 事件
 */
public class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source) {
        super(source);
    }
}
