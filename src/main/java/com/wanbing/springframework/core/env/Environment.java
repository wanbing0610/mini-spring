package com.wanbing.springframework.core.env;

public interface Environment {
    String[] getActiveProfiles();
    String[] getDefaultProfiles();
    boolean acceptsProfiles(String... profiles);
}
