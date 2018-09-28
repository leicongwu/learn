package com.lifestorm.learn.net.common.nio.morechat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by life_storm on 2018/5/3.
 */
public class ChatRoomServer {

    private Selector selector = null ;
    private static final String LOCAL_HOST = "127.0.0.1";
    private static final int PORT = 9999;
    private Charset charset = Charset.forName("UTF-8");
    private static HashSet<String> users = new HashSet<String>();
    private static String USER_EXISTS = "system message: user exit, please change a name";
    private static String USER_CONTENT_SPLIT = "#@#";
    private static boolean flag = false ;

    public void init() throws IOException {
        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(LOCAL_HOST,PORT),1024);
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server is listening now!");
        while (true) {
            try {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    dealWithSelectorKey(server,key);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void dealWithSelectorKey(ServerSocketChannel server, SelectionKey key) throws IOException {
        if( key.isAcceptable() ){
            SocketChannel sc = server.accept() ;
            sc.configureBlocking(false);
            sc.register(selector,SelectionKey.OP_READ);

            key.interestOps(SelectionKey.OP_ACCEPT);
            System.out.println("Server is listening from client:" + sc.socket().getLocalAddress()+"port:"+sc.socket().getPort());
            sc.write(charset.encode("please input your name."));
        }else if( key.isReadable() ){
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder content = new StringBuilder();
            try{
                int len = 0;
                while((len = sc.read(buffer)) > 0 ){
                    buffer.flip();
                    content.append(charset.decode(buffer));
                }
                //通道关闭
                if(len == -1) {
                    key.interestOps(SelectionKey.OP_CONNECT);
                    return;
                }
                System.out.println("Server is listening from client:"+ sc.socket().getLocalAddress()+"port:"+sc.socket().getPort()+"receive data is:" + content);
                key.interestOps(SelectionKey.OP_READ);
            }catch (Exception ex){
                key.cancel();
                if( key.channel() != null ){
                    key.channel().close();
                }
            }
            if( content.length() > 0 ) {
                String[] arrayContent = content.toString().split(USER_CONTENT_SPLIT);
                //注册用户
                if( arrayContent != null && arrayContent.length == 1 ){
                    String name = arrayContent[0] ;
                    if( users.contains(name)) {
                        sc.write(charset.encode(USER_EXISTS));
                    }else {
                        users.add(name);
                        int num = onLineNum(selector) ;
                        String message = "welcome" + name + " to chat room! online numbers:" + num ;
                        broadCast(selector,null,message);
                    }
                }else if( arrayContent != null && arrayContent.length > 1 ) {
                    String name = arrayContent[0];
                    String message = content.substring(name.length() + USER_CONTENT_SPLIT.length());
                    message = name + " say " + message ;
                    if( users.contains(name)){
                        broadCast(selector ,sc , message);
                    }
                }
            }
        }
    }

    //无法检测下线情况，只能轮循统计了
    public static int onLineNum(Selector selector) {
        int res = 0 ;
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel() ;
            if(targetChannel instanceof  SocketChannel ){
                res++;
            }
        }
        return res ;
    }

    public void broadCast(Selector selector, SocketChannel except, String content ) throws IOException {

        for (SelectionKey key : selector.keys() ){
            Channel targetChannel = key.channel();
            if( targetChannel instanceof SocketChannel && targetChannel !=except) {
                SocketChannel dest = (SocketChannel) targetChannel;
                dest.write(charset.encode(content));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatRoomServer().init();
    }

}
