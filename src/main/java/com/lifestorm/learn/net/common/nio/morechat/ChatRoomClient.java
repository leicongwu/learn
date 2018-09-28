package com.lifestorm.learn.net.common.nio.morechat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by life_storm on 2018/5/3.
 */
public class ChatRoomClient {


    private Selector selector = null ;
    private static final String LOCAL_HOST = "127.0.0.1";
    private static final int PORT = 9999;
    private Charset charset = Charset.forName("UTF-8");
    private SocketChannel sc = null ;
    private String name ="";
    private static String USER_EXISTS = "system message: user exit, please change a name";
    private static String USER_CONTENT_SPLIT = "#@#";

    public void init() throws IOException {
        selector = Selector.open() ;
        sc = SocketChannel.open(new InetSocketAddress(LOCAL_HOST,PORT));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
        new Thread(new ClientNio()).start();
        Scanner scanner = new Scanner(System.in) ;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if("".equals(line)){
                continue;
            }else if( "".equals(name)) {
                name = line ;
            }else {
                line = name + USER_CONTENT_SPLIT + line ;
            }
            sc.write(charset.encode(line));
        }

    }

    private class ClientNio implements Runnable {

        @Override
        public void run() {

            while (true) {
                try {
                    selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    SelectionKey key = null;
                    while (iterator.hasNext()) {
                        key = iterator.next();
                        iterator.remove();
                        dealWithSelectorKey(key);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        public void dealWithSelectorKey( SelectionKey key) throws IOException {

            if( key.isReadable() ){
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                String content = new String();
                try{
                    while(sc.read(buffer) > 0 ){
                        buffer.flip();
                        content += charset.decode(buffer);
                    }
                    if(USER_EXISTS.equals(content)) {
                        name = "";
                    }
                    System.out.println(content);
                    key.interestOps(SelectionKey.OP_READ);
                }catch (Exception ex){
                    key.cancel();
                    if( key.channel() != null ){
                        key.channel().close();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatRoomClient().init();
    }
}
