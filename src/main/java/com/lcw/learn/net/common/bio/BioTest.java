package com.lcw.learn.net.common.bio;

import java.util.Random;

/**
 * Created by leicongwu on 2018/4/15.
 */
public class BioTest {

    public static void main(String[] args) throws InterruptedException {
      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    new BioServer().start();
                }catch (Exception ex){
                    System.out.println("exception message is" + ex.getMessage());
                }
            }
        }).start();
        Thread.sleep(5000L);
        */
        final Random random = new Random(1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    BioClient bioClient = new BioClient();
                    String messageRandom = "message info is"+random.nextInt(1000);
                    bioClient.send(messageRandom);
                    try {
                        Thread.sleep(random.nextInt(1000));
                    }catch (Exception ex){
                        System.out.println("client exception is" +ex.getMessage());
                    }
                }
            }
        }).start();
    }
}
