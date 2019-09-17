package com.lifestorm.learn.io.search.tio;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.Node;

/**
 * Created by engle on 2019/7/10.
 */
public class HelloClientStarter {

  public static Node serverNode = new Node("127.0.0.1", Const.PORT);

  public static ClientAioHandler<Object, HelloPacket, Object> aioClientHandler =
      new HelloClientAioHandler();

  public static ClientAioListener<Object, HelloPacket, Object> aioListener = null;

  public static ReconnConf<Object, HelloPacket, Object> reconnConf =
      new ReconnConf<Object, HelloPacket, Object>(5000L);

  public static ClientGroupContext<Object, HelloPacket, Object> clientGroupContext =
      new ClientGroupContext<Object, HelloPacket, Object>(aioClientHandler, aioListener, reconnConf);

  public static AioClient<Object, HelloPacket, Object> aioClient = null;

  public static ClientChannelContext<Object, HelloPacket, Object> clientChannelContext = null;

  public static void main(String[] args) throws Exception {
    aioClient = new AioClient<Object, HelloPacket, Object>(clientGroupContext);
    clientChannelContext = aioClient.connect(serverNode);
    Scanner scanner = new Scanner(System.in);
    while(true){
      System.out.println("请输入发的消息信息:");
      String message = scanner.nextLine();
      if("bye".equals(message)){
        System.out.println("将退出");
        break;
      }else {
        send(message);
      }
    }


  }

  private static void send(String message) throws UnsupportedEncodingException {
    HelloPacket packet = new HelloPacket();
    packet.setBody(message.getBytes(HelloPacket.CHARSET));
    Aio.send(clientChannelContext,packet);
  }


}
