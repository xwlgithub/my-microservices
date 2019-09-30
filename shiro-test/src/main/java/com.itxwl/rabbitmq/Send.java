package com.itxwl.rabbitmq;

import com.itxwl.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * 消息的发送与接收
 */

/**
 * 生产者
 */
public class Send {
    public static final String QUEUE_NAME = "simple_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection=null;
        Channel channel=null;
        try {
             connection = ConnectionUtil.geConnection();
            //从连接中创建通道
             channel = connection.createChannel();
            //声明(创建)队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //消息内容
            for (int i = 1; i < 30; i++) {
                String message="你好 Rabbitmq  你是第 "+i+"名";
                //向指定的队列中发送消息
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            }
        } finally {
            channel.close();
            connection.close();
        }
    }
}
