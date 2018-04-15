package com.lcw.learn.net.common.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by leicongwu on 2018/4/15.
 */
public class NioClient {
    private static final String DEFALUT_HOST = "127.0.0.1";
    private static int DEFALUT_PORT = 12345;
    private static NioClientHandle clientHandle;
    public void start(){
        start(DEFALUT_HOST,DEFALUT_PORT);
    }

    public synchronized void start(String ip, int port) {
        System.out.println("client start");
        if( clientHandle != null ){
            clientHandle.stop();
        }
        clientHandle = new NioClientHandle(ip,port);
        new Thread(clientHandle,"CLIENT").start();
    }

    public static boolean sendMsg(String msg) throws  Exception {
        if(msg.equals("q")){
            return  false;
        }
        clientHandle.sendMsg(msg);
        return true ;

    }

    public static void main(String[] args) {
        NioClient nioClient = new NioClient();
        nioClient.start();
    }

    class NioClientHandle implements Runnable{
        private String host ;
        private int port;
        private Selector selector;
        private SocketChannel socketChannel ;
        private volatile boolean  started ;


        public NioClientHandle(String ip, int port) {
                this.host = ip ;
                this.port = port;
                try {
                    //创建选择器
                    selector = Selector.open();
                    //打开监听通道
                    socketChannel = SocketChannel.open();
                    //如果为true，表示为阻塞，如果为false,表示为非阻塞
                    socketChannel.configureBlocking(false);
                    started = true ;
                }catch (Exception ex ){
                    System.out.println(ex.getMessage());
                    System.exit(1);
                }
        }

        public void stop(){
                started = false ;
        }

        @Override
        public void run(){
                try{
                    //进行连接操作
                    boolean hasConnected = socketChannel.connect(new InetSocketAddress(host,port));
                    if(hasConnected){
                        socketChannel.register(selector, SelectionKey.OP_CONNECT);
                    }

                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                    System.exit(1);
                }
                //循环遍历selector
                while(started) {
                    try{
                        //无论是否有读写事件发生，selector每隔1S被唤醒一次
                        selector.select(1000);
                        //阻塞，只有当一个注册事件发生的时候才会继续
                        //selector.select();
                        Set<SelectionKey>  keys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = keys.iterator();
                        SelectionKey key = null;
                        while(iterator.hasNext()) {
                            key = iterator.next();
                            iterator.remove();
                            try {
                                //处理消息
                                if(key.isValid()) {
                                    SocketChannel sc = (SocketChannel)key.channel();
                                    if(!key.isConnectable()){
                                        System.exit(1);
                                    }
                                    //读消息
                                    if(key.isReadable()) {
                                        //创建ByterBuffer,并开辟一个1M的缓冲区
                                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                                        //读取请求流，返回读取到的字节数
                                        int readBytes = sc.read(buffer);
                                        //读取到字节，对字节进行编解码
                                        if(readBytes > 0 ){
                                            //将缓冲区当前的limit设置为position = 0用于后续对缓冲区的读取操作
                                            buffer.flip();
                                            byte[] bytes = new byte[buffer.remaining()];
                                            buffer.get(bytes);
                                            String result = new String(bytes,"UTF-8");
                                            System.out.println("Client receive message" + result);
                                        }else if( readBytes < 0 ){
                                            key.cancel();
                                            sc.close();
                                        }
                                    }
                                }

                            }catch (Exception ex){
                                if(key != null ){
                                    key.cancel();
                                    if(key.channel() != null ){
                                        key.channel().close();
                                    }
                                }
                            }
                        }
                    }catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        System.exit(1);
                    }
                }

                if(selector != null ){
                    try {
                        selector.close();
                    }catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }


        }

        public void sendMsg(String msg) throws Exception{
            socketChannel.register(selector,SelectionKey.OP_WRITE);
            //进行异步消息的发送
            //将消息编码为字节数组
            byte[] bytes = msg.getBytes();
            //根据数组容量创建ByteBuffer
            ByteBuffer writerBuffer = ByteBuffer.allocate(bytes.length);
            //将字节数组复制到缓冲区
            writerBuffer.put(bytes);
            //flip操作
            writerBuffer.flip();
            //发送缓冲区的字节数组
            socketChannel.write(writerBuffer);
        }
    }
}
