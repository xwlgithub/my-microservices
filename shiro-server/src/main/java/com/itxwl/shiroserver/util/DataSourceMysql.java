package com.itxwl.shiroserver.util;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceMysql {
//    @Value("${spring.datasource.url}")
//    private    String url;
//    @Value("${spring.datasource.username}")
//    private  String username;
//    @Value("${spring.datasource.password}")
//    private  String password;
    private static final DruidDataSource dataSource=new DruidDataSource();
    private DataSourceMysql(){
    }
    public  static Connection getDataSource() throws SQLException {
        dataSource.setUrl("jdbc:mysql://localhost:3306/microservices?characterEncoding=utf-8&useSSL=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource.getConnection(dataSource.getUsername(),dataSource.getPassword());
    }

}
