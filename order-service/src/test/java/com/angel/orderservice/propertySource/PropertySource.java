package com.angel.orderservice.propertySource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertySource {

    @Value("spring.datasource.driver-class-name")
    public String driverClassName;
    @Value("spring.datasource.username")
    public String username;
    @Value("spring.datasource.password")
    public String password;
    @Value("spring.datasource.url")
    public String url;

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}
