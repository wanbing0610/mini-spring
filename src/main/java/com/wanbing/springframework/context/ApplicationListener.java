package com.wanbing.springframework.context;

import java.util.EventListener;

/**
 * 应用监听
 */
public class ApplicationListener implements EventListener {
    /**
     * 接收事件
     * @param event
     */
    void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }
}
