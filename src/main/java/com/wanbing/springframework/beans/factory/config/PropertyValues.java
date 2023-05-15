package com.wanbing.springframework.beans.factory.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyValues {
    private final List<PropertyValue> propertyValues;

    public PropertyValues() {
        this.propertyValues = new ArrayList<>(10);
    }

    public List<PropertyValue> getPropertyValueList(){
        return this.propertyValues;
    }

    public int size(){
        return this.propertyValues.size();
    }

    public void addPropertyValue(PropertyValue propertyValue){
        this.propertyValues.add(propertyValue);
    }

    public void addPropertyValue(String propertyType, String propertyName, Object propertyValue){
        addPropertyValue(new PropertyValue(propertyType, propertyName, propertyValue));
    }

    public void removePropertyValue(PropertyValue propertyValue){
        this.propertyValues.remove(propertyValue);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValues.toArray(new PropertyValue[this.propertyValues.size()]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValues) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

    public void removePropertyValue(String propertyName){
        this.propertyValues.remove(getPropertyValue(propertyName));
    }

    public boolean contains(String propertyName) {
        return (getPropertyValue(propertyName) != null);
    }

    public boolean isEmpty() {
        return this.propertyValues.isEmpty();
    }







}
