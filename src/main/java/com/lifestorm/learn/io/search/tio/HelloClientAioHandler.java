package com.lifestorm.learn.io.search.tio;

import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;

/**
 * Created by engle on 2019/7/10.
 */
public class HelloClientAioHandler extends HelloAbsAioHandler
    implements ClientAioHandler<Object,HelloPacket,Object> {

  private static HelloPacket hearbeatPacket = new HelloPacket();

  @Override
  public HelloPacket heartbeatPacket() {
    return hearbeatPacket;
  }

  @Override
  public Object handler(HelloPacket helloPacket,
      ChannelContext<Object, HelloPacket, Object> channelContext) throws Exception {
    byte[] body = helloPacket.getBody();
    if( body != null ){
      String str = new String(body,HelloPacket.CHARSET);
      System.out.println("收到的消息:"+str);
    }
    return null;
  }
}
