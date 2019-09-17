package com.lifestorm.learn.io.search.tio;

import org.tio.core.intf.Packet;

/**
 * Created by engle on 2019/7/10.
 */
public class HelloPacket extends Packet{

  public static final int HEADER_LENGTH = 4;

  public static final String CHARSET = "utf-8";

  private byte[] body;

  public byte[] getBody() {
    return body;
  }

  public void setBody(byte[] body) {
    this.body = body;
  }
}
