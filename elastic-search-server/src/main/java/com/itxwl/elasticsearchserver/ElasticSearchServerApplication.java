package com.itxwl.elasticsearchserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableEurekaClient
@EnableFeignClients
@EnableTransactionManagement
@SpringBootApplication
public class ElasticSearchServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchServerApplication.class, args);
    }

}
