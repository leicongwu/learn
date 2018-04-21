package com.lcw.learn.net.common.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by leicongwu on 2018/4/15.
 */
public class NioServer {
    private static final int DEFAULT_PORT =12345;
    private static final String DEFALUT_HOST = "127.0.0.1";
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
            if (key.isValid()) {
                //处理新接入的请求消息
                if (key.isAcceptable()) {
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    //通过ServerSocketChannel的accept创建SocketChannel实例
                    //完成该操作意味着完成TCP三次握手，TCP物理链路正式建立
                    SocketChannel sc = ssc.accept();
                    //设置为非阻塞的
                    sc.configureBlocking(false);
                    //注册为读
                    sc.register(selector, SelectionKey.OP_READ);
                }

                //读消息
                if (key.isReadable()) {
                    System.out.println("start read!" + new Date());
                    SocketChannel sc = (SocketChannel) key.channel();
                    //创建ByteBuffer，并开辟一个1M的缓冲区
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(buffer);
                    if (readBytes > 0) {
                        //将缓冲区当前的limit设置为position=0 ，用于后续对缓冲区的读取操作。
                        buffer.flip();
                        //根据缓冲区可读字节创建字节数组
                        byte[] bytes = new byte[buffer.remaining()];
                        //将缓冲区可读字节数组复制到新建的数组中
                        buffer.get(bytes);
                        String message = new String(bytes, "UTF-8");
                        /*
                        //响应客户端消息
                        //将消息编码为字节数组
                        byte[] clientBytes = message.getBytes();
                        //根据数组容量创建ByteBuffer
                        ByteBuffer writerBuffer = ByteBuffer.allocate(clientBytes.length);
                        //将字节数组复制到缓冲区
                        writerBuffer.put(clientBytes);
                        //flip操作
                        writerBuffer.flip();
                        sc.write(writerBuffer);
                        */
                        System.out.println("client message is" +message);
                        key.interestOps(SelectionKey.OP_WRITE);
                    } else if (readBytes < 0) {
                        key.cancel();
                        sc.close();
                    }
                }

                //写消息
                if (key.isWritable()) {
                    System.out.println("start write!" + new Date());
                    SocketChannel sc = (SocketChannel) key.channel();
                    //创建ByteBuffer，并开辟一个1M的缓冲区
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(buffer);
                    if (readBytes > 0) {
                        //将缓冲区当前的limit设置为position=0 ，用于后续对缓冲区的读取操作。
                        buffer.flip();
                        //根据缓冲区可读字节创建字节数组
                        byte[] bytes = new byte[buffer.remaining()];
                        //将缓冲区可读字节数组复制到新建的数组中
                        buffer.get(bytes);
                        String message = new String(bytes, "UTF-8");
                        //响应客户端消息
                        //将消息编码为字节数组
                        byte[] clientBytes = message.getBytes();
                        //根据数组容量创建ByteBuffer
                        ByteBuffer writerBuffer = ByteBuffer.allocate(clientBytes.length);
                        //将字节数组复制到缓冲区
                        writerBuffer.put(clientBytes);
                        //flip操作
                        writerBuffer.flip();
                        sc.write(writerBuffer);
                    } else if (readBytes < 0) {
                        key.cancel();
                        sc.close();
                    }
                }
            }
        }
    }
}
