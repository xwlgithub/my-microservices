package com.itxwl.getoutserver.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ConfigurationMaster {
    @Value("${spring.datasource.dynamic.datasource.master.url}")
    private String url;
    @Value("${spring.datasource.dynamic.datasource.master.username}")
    private String username;
    @Value("${spring.datasource.dynamic.datasource.master.password}")
    private String password;
    @Value("${spring.datasource.dynamic.datasource.master.driver-class-name}")
    private String driverClassName;
}
