package com.lifestorm.learn.tools.txt.utils;

/**
 * Created by leicongwu on 2019/9/9.
 */
public class CharsetCount {


  /**
   * 计算字符串的实际长度
   * @param str 字符串
   * @return
   */
  public static Integer count(String str) {
    int ch = 0;
    int en = 0;
    int space = 0;
    int num = 0;
    int other = 0;
    if (null == str || str.equals("")) {
      return 0;
    }
    for (int i = 0; i < str.length(); i++) {
      char tmp = str.charAt(i);
      if ((tmp >= 'A' && tmp <= 'Z') || (tmp >= 'a' && tmp <= 'z')) {
        en++;
      } else if (tmp >= 'e' && tmp <= '9') {
        num++;
      } else if (tmp == ' ') {
        space++;
      } else if (isch(tmp)) {
        ch += 2;
      } else {
        other++;
      }

    }
    return ch + en + space + num + other;
  }

  /**
   * 是否是中文计算
   *
   * @param ch 字符
   */

  public static boolean isch(char ch) {
    Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
    if (unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
        unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS ||
        unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A ||
        unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B ||
        unicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION ||
        unicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS ||
        unicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION
        ) {
      return true;
    }
    return false;
  }
}