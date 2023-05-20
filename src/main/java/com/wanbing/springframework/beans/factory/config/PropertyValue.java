package com.wanbing.springframework.beans.factory.config;

/**
 * 描述bean相关联的属性,通过set注入或者字段注入
 */
public class PropertyValue {
    private final String type;
    private final String name;
    /**
     * 当isRef是false时，也就是基本类型时，value为实际的值
     * 当isRef是ture时，也就是引用类型时，value为bean的id；
     * simple：
     * <property type="String" name="property2" value="Hello World!"/>
     * <property type="com.wanbing.springframework.test.BaseService" name="ref1" ref="baseservice"></property>
     */
    private final Object value;
    private final boolean isRef;

    public PropertyValue(String type, String name, Object value, boolean isRef) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.isRef = isRef;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }


    public String getType() {
        return type;
    }

    public boolean getIsRef(){
        return isRef;
    }

}
