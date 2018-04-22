package com.lcw.learn.net.common.nio;

import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * Created by leicongwu on 2018/4/22.
 */
public class EchoClient {
    private LinkedList<ByteBuffer>  messageQuen = new LinkedList<ByteBuffer>();

    public LinkedList<ByteBuffer> getMessageQuen() {
        return messageQuen;
    }

    public void addMsg(ByteBuffer byteBuffer) {
        messageQuen.addFirst(byteBuffer);
    }
}
