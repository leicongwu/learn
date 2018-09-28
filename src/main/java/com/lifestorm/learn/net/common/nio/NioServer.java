package com.lifestorm.learn.net.common.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by life_storm on 2018/4/15.
 */
public class NioServer {
    private static final int DEFAULT_PORT =12345;
    private static final String DEFALUT_HOST = "127.0.0.1";
    private ExecutorService tp = Executors.newCachedThreadPool();
    public static Map<Socket,Long> time_stat = new HashMap<Socket, Long>(10240);
    private static  NioServerHandle serverHandle;

    public void start(){
        start(DEFAULT_PORT);
    }

    public  synchronized void start(int port){
        System.out.println("server start");
        if(serverHandle != null ){
            serverHandle.stop();
        }
        serverHandle = new NioServerHandle(port);
        new Thread(serverHandle,"SERVER").start();
    }


    class NioServerHandle implements Runnable {

        private Selector selector;
        private ServerSocketChannel serverSocketChannel;
        private volatile boolean started;

        private NioServerHandle(int port) {
            try {
                //创建选择器
                selector = Selector.open();
                //打开监听通道
                serverSocketChannel = ServerSocketChannel.open();
                //如果为true,则此通道将被至于阻塞模式；如果为false，则此通道将置于非阻塞模式，如果为false，则此通道将被置于阻塞模式
                serverSocketChannel.configureBlocking(false);
                //绑定端口 backlog设为1024
                serverSocketChannel.socket().bind(new InetSocketAddress(DEFALUT_HOST,port), 1024);
                //监听客户端连接请求
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                started = true;
                System.out.println("server has started,port is" + port);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.exit(1);
            }
        }

        public void stop() {
            started = false;
        }

        @Override
        public void run() {
            //遍历selector
            while (started) {
                try {
                    //无论是否读写事件发生，selector每隔1s唤醒一次
                    selector.select();
                    //阻塞，只有当至少一个注册的事件发生的时候才会继续,此次不使用
                    //selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    SelectionKey key = null;
                    while (iterator.hasNext()) {
                        key = iterator.next();
                        iterator.remove();
                        //处理链接
                        try {
                            handleSelector(key);
                        } catch (Exception ex) {
                            if (key == null) {
                                key.cancel();
                                if (key.channel() != null) {
                                    key.channel().close();
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            //selector关闭后悔自动释放里面管理的资源
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleSelector(SelectionKey key) throws IOException {
            if(key.isAcceptable()){
                doAccept(key);
            }else if( key.isValid() && key.isReadable()) {
                Socket socket = ((SocketChannel)(key.channel())).socket();
                if(!time_stat.containsKey(socket)){
                    time_stat.put(socket,System.currentTimeMillis());
                }
                doRead(key);
            }else if( key.isValid() && key.isWritable()) {
                doWrite(key);
                long end = System.currentTimeMillis();
                Socket socket = ((SocketChannel)(key.channel())).socket();
                long start = time_stat.remove(socket);
                System.out.println("spend time "+(end-start)+"ms");
            }

        }
        private void doAccept(SelectionKey key) {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel clientChannel;
            try{
                clientChannel = server.accept();
                clientChannel.configureBlocking(false);
                SelectionKey clientKey = clientChannel.register(selector,SelectionKey.OP_READ);
                //连接后进行处理
                EchoClient echoClient = new EchoClient();
                key.attach(echoClient);

                InetAddress clientAddress = clientChannel.socket().getInetAddress();
                System.out.println("accept connect from ip:" + clientAddress.getHostAddress()+"；port："+clientChannel.socket().getPort());
            }catch (Exception ex) {
                System.out.println("fail to accept client");
                ex.printStackTrace();
            }

        }

        private void doRead(SelectionKey key) {
            SocketChannel clientChannel = (SocketChannel) key.channel();
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            int len = 0;
            try{
                len = clientChannel.read(readBuffer);

               if( len < 0 ){
                    disconnected(key);
                }
                System.out.println("server read:");
                System.out.println(new String(readBuffer.array(),"UTF-8"));
            }catch (Exception ex){
                System.out.println("fail to read from client");
                ex.printStackTrace();
                disconnected(key);
                return;
            }
            readBuffer.flip();
            ByteBuffer serverBuffer = ByteBuffer.allocate(1024);
            serverBuffer.put(new String("hello client").getBytes());
            serverBuffer.flip();
            try {
                clientChannel.write(serverBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            tp.execute(new HandleMsg(key,readBuffer,selector));
        }


        private void doWrite(SelectionKey key) {
            SocketChannel channel =  (SocketChannel)key.channel();
            EchoClient echoClient = (EchoClient) key.attachment();
            LinkedList<ByteBuffer> q = echoClient.getMessageQuen();
            ByteBuffer writerBuffer = q.getLast();
            try{
                System.out.println("正在写入到客户端信息...");
                int len = channel.write(writerBuffer);
                if( len == -1) {
                    disconnected(key);
                    return;
                }
                if( writerBuffer.remaining() == 0 ){
                    q.removeLast();
                }
            }catch (Exception ex) {
                System.out.println("failed to write to client");
                ex.printStackTrace();
                disconnected(key);
            }
            System.out.println("read msg");
            if( q.size() == 0 ) {
                key.interestOps(SelectionKey.OP_WRITE);
            }
        }

        private void disconnected(SelectionKey key) {
            try {
                key.channel().close();
                System.out.println("close client !");
            } catch (IOException e) {
                System.out.println("failed to close channel!");
                e.printStackTrace();
            }
        }


    }
}
