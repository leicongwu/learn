package com.lifestorm.learn.io.search.tio;

import java.io.IOException;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

/**
 * Created by engle on 2019/7/10.
 */
public class HelloServerStarter {

  public static ServerAioHandler<Object,HelloPacket,Object> aioHandler = new HelloServerAioHandler();
  public static ServerAioListener<Object, HelloPacket, Object> aioListener = null;
  public static ServerGroupContext<Object, HelloPacket, Object> serverGroupContext = new ServerGroupContext<Object, HelloPacket, Object>(aioHandler,aioListener);

  public static AioServer<Object, HelloPacket, Object> aioServer = new AioServer<Object, HelloPacket, Object>(serverGroupContext);

  public static String serverIp = null;

  public static int serverPort = Const.PORT;

  public static void main(String[] args) throws IOException {
    aioServer.start(serverIp,serverPort);
  }




















}
