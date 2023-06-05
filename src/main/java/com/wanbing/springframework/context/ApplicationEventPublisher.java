package com.wanbing.springframework.context;

/**
 * 应用事件发布者
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);

    void addApplicationListener(ApplicationListener listener);
}
