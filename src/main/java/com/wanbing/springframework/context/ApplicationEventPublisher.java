package com.wanbing.springframework.context;

/**
 * 事件发布
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
