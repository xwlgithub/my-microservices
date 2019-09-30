package com.itxwl.rabbitmq;

import com.itxwl.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
@SuppressWarnings("all")
public class Recv2 {
    public static final String QUEUE_NAME = "simple_name";

    public static void main(String[] args) throws Exception{
        Connection connection = ConnectionUtil.geConnection();
        //从连接中创建通道
       final Channel channel = connection.createChannel();
        //设置每个消费者 同时只能取一条消息
        channel.basicQos(1);
        //声明(创建)队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        DefaultConsumer consumer=new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body);
                System.out.println("【消费者2】接收"+"\t"+msg+"");
                try {
                    //休眠5毫秒
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                //手动确认消息消费ACK
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //监听队列,第二个参数,是否自动进行消息确认
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
