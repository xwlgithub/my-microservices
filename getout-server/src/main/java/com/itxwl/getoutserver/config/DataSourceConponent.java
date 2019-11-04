package com.itxwl.getoutserver.config;

import lombok.AllArgsConstructor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;


@Configuration
//@AllArgsConstructor
@SuppressWarnings("all")
public class DataSourceConponent {
    @Autowired
private ConfigurationMaster configurationMaster;
    @Autowired
private ConfigurationOther configurationOther;
    @Bean(name = "master")
    public DataSource masterDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(configurationMaster.getUrl());
        dataSource.setUsername(configurationMaster.getUsername());
        dataSource.setPassword(configurationMaster.getPassword());
        dataSource.setDriverClassName(configurationMaster.getDriverClassName());
        return dataSource;
    }

    @Bean(name = "other")
    public DataSource slaveDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(configurationOther.getUrl());
        dataSource.setUsername(configurationOther.getUsername());
        dataSource.setPassword(configurationOther.getPassword());
        dataSource.setDriverClassName(configurationOther.getDriverClassName());
        return dataSource;
    }

    @Primary//不加这个会报错。
    @Bean(name = "multiDataSource")
    public MultiRouteDataSource exampleRouteDataSource() {
        MultiRouteDataSource multiDataSource = new MultiRouteDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource());
        targetDataSources.put("other", slaveDataSource());
        multiDataSource.setTargetDataSources(targetDataSources);
        multiDataSource.setDefaultTargetDataSource(masterDataSource());
        return multiDataSource;
    }
}
