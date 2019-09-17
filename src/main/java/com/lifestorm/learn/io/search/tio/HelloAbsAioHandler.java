package com.lifestorm.learn.io.search.tio;

import java.nio.ByteBuffer;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.AioHandler;

/**
 * Created by engle on 2019/7/10.
 */
public abstract class HelloAbsAioHandler implements AioHandler<Object,HelloPacket,Object>{

  @Override
  public ByteBuffer encode(HelloPacket helloPacket,
      GroupContext<Object, HelloPacket, Object> groupContext,
      ChannelContext<Object, HelloPacket, Object> channelContext) {
    byte[] body = helloPacket.getBody();
    int bodyLen = 0 ;
    if( body !=null ){
      bodyLen = body.length;
    }
    int allLen = HelloPacket.HEADER_LENGTH + bodyLen;
    ByteBuffer buffer = ByteBuffer.allocate(allLen);
    buffer.order(groupContext.getByteOrder());
    buffer.putInt(bodyLen);
    if( body != null){
      buffer.put(body);
    }
    return buffer;
  }

  @Override
  public HelloPacket decode(ByteBuffer byteBuffer,
      ChannelContext<Object, HelloPacket, Object> channelContext) throws AioDecodeException {
    int readableLen = byteBuffer.limit() - byteBuffer.position();
    if( readableLen < HelloPacket.HEADER_LENGTH){
      return null;
    }
    int bodyLen = byteBuffer.getInt();
    if( bodyLen < 0 ){
      throw new AioDecodeException("bodyLen["+bodyLen+"]is not right,remote:"+channelContext.getClientNode());
    }
    int needLen = HelloPacket.HEADER_LENGTH + bodyLen;
    int dataEnough = readableLen - needLen;
    if( dataEnough < 0 ){
      return null;
    }else {
      HelloPacket imPacket = new HelloPacket();
      if( bodyLen > 0 ){
        byte[] dst = new byte[bodyLen];
        byteBuffer.get(dst);
        imPacket.setBody(dst);
      }
      return imPacket;
    }
  }
}
