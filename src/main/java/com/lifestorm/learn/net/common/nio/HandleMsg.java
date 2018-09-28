package com.lifestorm.learn.net.common.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * Created by life_storm on 2018/4/22.
 */
public class HandleMsg implements Runnable {

    private SelectionKey sk ;
    ByteBuffer bb ;
    Selector selector;
    public HandleMsg(SelectionKey key, ByteBuffer readBuffer, Selector selector) {
            this.sk = key;
            this.bb = readBuffer;
            this.selector =selector ;
    }

    @Override
    public void run() {
            EchoClient echoClient = (EchoClient)sk.attachment();
            if (echoClient == null ){
                return;
            }
            echoClient.addMsg(bb);
            //强迫selector立即返回
            selector.wakeup();
    }
}
