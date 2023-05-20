package com.wanbing.springframework.beans.factory.config;

/**
 * 描述bean关联的属性,通过构造器注入
 */
public class ArgumentValue {
    /**
     * 关联的Bean的实例
     */
    private Object value;
    /**
     * 关联Bean的类型
     */
    private String type;
    /**
     * 关联Bean的形参
     */
    private String name;

    public ArgumentValue(String type, Object value) {
        this.value = value;
        this.type = type;
    }

    public ArgumentValue(String type, String name,Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
