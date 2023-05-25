package com.wanbing.springframework.test.reflect;

import java.lang.reflect.Field;

/**
 * 1.set注入 调用set方法复制
 * 2.属性注入 filed
 */
public class User {
    private String name;

    public User() {
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        User user = new User();
        System.out.println(user);
        Class<? extends User> clazz = user.getClass();


        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        field.set(user, "wanbing");

        System.out.println(user);
    }
}
