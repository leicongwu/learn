package com.lcw.learn.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by leicongwu on 2018/3/18.
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
