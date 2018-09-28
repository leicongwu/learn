package com.lifestorm.learn.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by life_storm on 2018/3/18.
 */
public class TopReceive {


    private String username =   "";
    private String password = "";
    private String brokerUrl="tcp://192.168.1.222:61616";
    private ConnectionFactory factory ;
    private Connection connection;
    private Session session ;
    private Destination destination ;
    private MessageConsumer consumer ;

    public static void main(String[] args) {
        new TopReceive().start();
    }

    public void start() {
        try {
            factory = new ActiveMQConnectionFactory(username,password,brokerUrl);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("topic-msg");
            consumer = session.createConsumer(destination);
            Message message = consumer.receive();
            while(true){
                String textMessage = ((TextMessage)message).getText();
                System.out.println("接收到的信息为"+textMessage);
                message = consumer.receive(1000L);
            }
        }catch (Exception ex){

        }finally {

        }
    }
}
