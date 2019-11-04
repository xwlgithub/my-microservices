package com.itxwl.getoutserver.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ConfigurationOther {
    @Value("${spring.datasource.dynamic.datasource.other.url}")
    private String url;
    @Value("${spring.datasource.dynamic.datasource.other.username}")
    private String username;
    @Value("${spring.datasource.dynamic.datasource.other.password}")
    private String password;
    @Value("${spring.datasource.dynamic.datasource.other.driver-class-name}")
    private String driverClassName;
}
