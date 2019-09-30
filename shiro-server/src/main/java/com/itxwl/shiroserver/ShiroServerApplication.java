package com.itxwl.shiroserver;

import com.itxwl.shiroserver.util.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class ShiroServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroServerApplication.class, args);
    }
}
