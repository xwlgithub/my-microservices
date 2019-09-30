package com.itxwl.utils;


import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import com.rabbitmq.client.Connection;

import java.util.concurrent.TimeoutException;
public class ConnectionUtil {
    public static Connection geConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("192.168.0.108");
        /**
         * 客户端连接 默认端口5672
         */
        connectionFactory.setPort(5672);
        /**
         * 用户名
         */
        connectionFactory.setVirtualHost("/xwl");
        connectionFactory.setUsername("xwl");
        connectionFactory.setPassword("258000");
        Connection connection = connectionFactory.newConnection();
        return connection;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.geConnection();
        System.out.println(connection);
    }
}
