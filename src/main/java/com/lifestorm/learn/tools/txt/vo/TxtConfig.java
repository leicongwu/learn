package com.lifestorm.learn.tools.txt.vo;

import java.util.List;

public class TxtConfig<T> {

  /**
   * 数据集合
   */
  private final List<T> objList;
  /**
   * 数据所属的类型
   */
  private final Class<T> cls;
  /**
   * 列名，也就是对应的实体的变量名称
   */
  private final String[] columnName;
  /**
   * 标题名称,展示的名称
   */
  private final String[] titleName;
  /**
   *日期的格式，默认为yyyy-MM-dd HH:mm:ss
   */
  private final String format;
  /**
   * 文长度，中文长度通常为2个字符长度，如果长度为负数，默认为50
   */
  private int chineseLen;

  /**
   * @param objList     数据集合
   * @param cls         数据所属的类型
   * @param columnName  列名，也就是对应的实体的变量名称
   * @param titleName  标题名称
   * @param dateformat   日期的格式，默认为yyyy-MM-dd HH:mm:ss
   * @param chineseLen 中文长度，中文长度通常为2个字符长度，如果长度为负数，默认为50
   */
  public TxtConfig(List<T> objList, Class<T> cls, String[] columnName, String[] titleName,
      String dateformat, int chineseLen) {
    this.objList = objList;
    this.cls = cls;
    this.columnName = columnName;
    this.titleName = titleName;
    this.format = dateformat;
    this.chineseLen = chineseLen;
  }

  public List<T> getObjList() {
    return objList;
  }

  public Class<T> getCls() {
    return cls;
  }

  public String[] getColumnName() {
    return columnName;
  }

  public String[] getTitleName() {
    return titleName;
  }

  public String getFormat() {
    return format;
  }

  public int getChineseLen() {
    return chineseLen;
  }

  public void setChineseLen(int chineseLen) {
    this.chineseLen = chineseLen;
  }
}
