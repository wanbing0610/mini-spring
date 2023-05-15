package com.wanbing.springframework.beans.factory.config;

/**
 * 描述bean相关联的属性,通过set注入或者字段注入
 */
public class PropertyValue {
    private final String type;
    private final String name;
    private final Object value;

    public PropertyValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
