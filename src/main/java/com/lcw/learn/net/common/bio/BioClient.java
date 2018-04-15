package com.lcw.learn.net.common.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by leicongwu on 2018/4/15.
 */
public class BioClient {

    private final static int DEFAULT_SERVER_PORT = 12345;

    private final static String DEFAULT_SERVER_IP = "127.0.0.1";

    public void send(String message) {
        send(DEFAULT_SERVER_PORT,message);
    }

    public void send(int port, String message) {
//        System.out.println("prepare send message is :" +message);
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            socket = new Socket(DEFAULT_SERVER_IP, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new PrintStream(socket.getOutputStream(),true));
            out.println(message);
            out.flush();
            System.out.println("client->server:"+in.readLine());
        }catch (Exception ex) {
            System.out.println("client send message has exception is" +ex.getMessage());
        }finally{
            if(out != null ){
                try{
                    out.close();
                    out = null;
                }catch (Exception ex){
                    System.out.println("close out has exception " +ex.getMessage());
                }
            }

            if(in != null ){
                try{
                    in.close();
                    in = null;
                }catch (Exception ex){
                    System.out.println("close in has exception " +ex.getMessage());
                }
            }

            if(socket != null ){
                try{
                    socket.close();
                    socket = null;
                }catch (Exception ex){
                    System.out.println("close socket has exception " +ex.getMessage());
                }
            }
        }
    }

}
