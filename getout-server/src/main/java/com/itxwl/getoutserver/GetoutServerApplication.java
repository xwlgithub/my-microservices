package com.itxwl.getoutserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
/**
 * 开启远程调用注解
 */
@EnableFeignClients
@EnableSwagger2
@ComponentScan({"com.itxwl.getoutserver.config","com.itxwl.getoutserver.controller"})
public class GetoutServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetoutServerApplication.class, args);
    }

}
