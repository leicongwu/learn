package com.lifestorm.learn.net.common.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by life_storm on 2018/4/13.
 *
 * @author life_storm
 *         since 1.0
 *         Modification History:
 *         Date         Author      Version     Description
 *         ------------------------------------------------------------------
 */
public class BioExecutorServer {

     // 默认端口号
    private static final int PORT= 12345 ;

    //服务端，单实例连接
    private static ServerSocket serverSocket ;

    private static final ExecutorService service = Executors.newFixedThreadPool(60);

    public void start() throws IOException{
        start(PORT);
    }

    public synchronized void start(int port) throws IOException{
        if(serverSocket != null){
            return;
        }
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务端已经启动，端口号为" + port);
            while (true) {
                Socket socket = serverSocket.accept();
                service.execute(new ServerHandler(socket));
            }
        }finally {
            if(serverSocket != null) {
               serverSocket.close();
                serverSocket = null;
            }
        }
    }

    class ServerHandler implements Runnable {

        private Socket socket = null;

        public ServerHandler(Socket socket){
            this.socket = socket ;
        }
        @Override
        public void run(){
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                in = new BufferedReader(new InputStreamReader( socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(),true);
                String message = null;
                while(true) {
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                        out.println("server receive client message:["+message+"]success");
                        out.flush();
                    }
                    break;
                }

            }catch (Exception ex){
                System.out.println(ex);
            }finally {
                    if( in != null) {
                        try {
                            in.close();
                            in = null;
                        }catch(Exception ex) {
                            System.out.println("in close exception is "+ex.getMessage());
                        }
                    }
                if( out != null) {
                    try {
                        out.close();
                        out = null;
                    }catch(Exception ex) {
                        System.out.println("out close exception is "+ex.getMessage());
                    }
                }

                if( socket != null) {
                    try {
                        socket.close();
                        socket = null;
                    }catch(Exception ex) {
                        System.out.println("socker close exception is "+ex.getMessage());
                    }
                }

            }
        }
    }

    public static void main(String[] args) throws IOException {
        new BioExecutorServer().start();
    }
}
