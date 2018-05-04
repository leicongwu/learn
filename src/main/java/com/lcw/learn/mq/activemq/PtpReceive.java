package com.lcw.learn.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by leicongwu on 2018/3/18.
 *   1、ConnectionFactory的生命周期  此对象不要频繁创建  全局创建一个就好 每次用该对象获取Connection就好了
 *   1、DeliveryMode  持久化方式，开发中可以选择对Message是否要做持久化，持久化的Message在重启amq后还会依然存在，非持久化会自动被干掉（但是会临时存放于amp），
 *   其值为NON_PERSISTENT非持久化 ，PERSISTENT持久化
 *   2、ACK（Acknowledgement），先说下什么是ACK,ACK即确认字符，在数据通信中，接收站发给发送站的一种传输类控制字符。表示发来的数据已确认接受无误。ACK信号通常是一个ASCII字符，不同的协议中ACK信号都不一样。
 *   当Consumer拿到Message之后要通知Broker已确认接收，否则其他Consumer会认为该Message依然可用，JMS的Session中提供了四种ACK_MODE
 *   AUTO_ACKNOWLEDGE = 1    自动确认
 *   CLIENT_ACKNOWLEDGE = 2    客户端手动确认
 *   DUPS_OK_ACKNOWLEDGE = 3    自动批量确认
 *   SESSION_TRANSACTED = 0    事务提交并确认
 */
public class PtpReceive {

    private String username =   "";
    private String password = "";
    private String brokerUrl="tcp://192.168.1.222:61616";
    private ConnectionFactory factory ;
    private Connection connection;
    private Session session ;
    private Destination destination ;
    private MessageConsumer consumer ;

    public static void main(String[] args) {
        new PtpReceive().start();
    }

    public void start() {
        try {
            factory = new ActiveMQConnectionFactory(username,password,brokerUrl);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("text-msg");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        String text = ((TextMessage)message).getText();
                        System.out.println("接收到的信息为"+text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("接收消息成功");
            consumer.close();
        }catch (Exception ex){

        }finally {

        }
    }
}
