package com.lcw.learn.net.common.nio;

/**
 * Created by leicongwu on 2018/4/15.
 */
public class NioTest {

    public static void main(String[] args) throws Exception{
        new NioServer().start();
        Thread.sleep(2000);
       /* NioClient nioClient = new NioClient();
        nioClient.start();
        while(nioClient.sendMsg(new Scanner(System.in).nextLine())){
            nioClient.sendMsg("你好啊");
        }*/
    }
}
