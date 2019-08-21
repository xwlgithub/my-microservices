package com.itxwl.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceMysql {
    private static final DruidDataSource dataSource=new DruidDataSource();
    private DataSourceMysql(){
    }
    public static Connection getDataSource() throws SQLException {
        dataSource.setUrl("jdbc:mysql://localhost:3306/microservices?characterEncoding=utf-8&useSSL=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource.getConnection(dataSource.getUsername(),dataSource.getPassword());
    }
}
