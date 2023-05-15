package com.wanbing.springframework.beans.factory.config;

import java.util.*;

public class ArgumentValues{
    private final List<ArgumentValue> argumentValueList = new ArrayList<>();

    public ArgumentValues() {
    }

    public void addArgumentValue(Integer key, ArgumentValue newValue){
        this.argumentValueList.add(key, newValue);
    }

    public ArgumentValue getIndexedArgumentValue(int index) {
        return this.argumentValueList.get(index);
    }

    public int getArgumentCount() {
        return (this.argumentValueList.size());
    }

    public boolean isEmpty() {
        return (this.argumentValueList.isEmpty());
    }


}
