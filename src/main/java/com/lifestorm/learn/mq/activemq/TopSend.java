package com.lifestorm.learn.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by life_storm on 2018/3/18.
 */
public class TopSend {
    private String username =   "";
    private String password = "";
    private String brokerUrl="tcp://192.168.1.222:61616";
    private ConnectionFactory factory ;
    private Connection connection;
    private Session session ;
    private Destination destination ;
    private MessageProducer producer ;

    public static void main(String[] args) {
        new TopSend().start();
    }

    public void start() {
        try {
            factory = new ActiveMQConnectionFactory(username,password,brokerUrl);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("topic-msg");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage textMessage = session.createTextMessage("呵呵");
            for (int i = 0 ; i < 100 ; i++){
                textMessage.setText("message:"+i);
                producer.send(textMessage);
            }

            System.out.println("发送消息成功");
            producer.close();
        }catch (Exception ex){

        }finally {

        }
    }
}
