package com.lifestorm.learn.io.search.tio;

import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.server.intf.ServerAioHandler;

/**
 * Created by engle on 2019/7/10.
 */
public class HelloServerAioHandler extends HelloAbsAioHandler
    implements ServerAioHandler<Object,HelloPacket,Object> {


  @Override
  public Object handler(HelloPacket helloPacket,
      ChannelContext<Object, HelloPacket, Object> channelContext) throws Exception {
    byte[] body = helloPacket.getBody();
    if( body != null ){
      String str = new String(body,HelloPacket.CHARSET);
      System.out.println("收到的消息："+str);
      HelloPacket responsePacket = new HelloPacket();
      responsePacket.setBody(("收到了你的消息,你的消息是:"+str).getBytes(HelloPacket.CHARSET));
      Aio.send(channelContext,responsePacket);
    }
    return null;
  }
}
