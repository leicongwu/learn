package com.lifestorm.learn.tools.txt.vo;

import com.lifestorm.learn.tools.txt.utils.CharsetCount;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * 代表一个列
 * Created by leicongwu on 2019/9/9.
 */
public class CellVo {

  private String ENCODING = "UTF-8";
  /**
   * 列宽的最小倍数
   */
  private static final  int MIN_LIMIT = 6 ;
  /**
   * 多少行
   */
  private int row;
  /**
   * 多少列
   */
  private int cell;
  /**
   * 文字的真实长度
   */
  private int realLen;

  /**
   * 在java中 String.length()长度
   */
  private int javaLen;

  /**
   * 文本内容
   */
  private String content;

  /**
   * 是否自动换行
   */
  private boolean lineWrap;

  /**
   * 文本内容列宽
   */
  private int lineCount;
  /**
   * 换行后的列表数据
   */
  private List<CellVo> childList = new ArrayList<CellVo>();;

  /**
   * 有多少子列
   */
//  private int childCount;

  private  byte[] contextBytes = null;

  /**
   *
   * @param row 行数
   * @param cell 列数
   * @param content  内容
   * @param lineCount 每行显示的最大长度，此长度为java的长度，需要保证行数为6的倍数
   * @param lineWrap
   */
  public CellVo(int row, int cell, String content, int lineCount, boolean lineWrap) {
    if(lineCount < MIN_LIMIT ){
      throw new IllegalArgumentException("列宽需要大于6个字符，当前:"+lineCount);
    }
    if(StringUtils.isEmpty(content)){
      content = "";
    }
    this.row = row;
    this.cell = cell;
    realLen = CharsetCount.count(content);
    this.javaLen = content.length();
    this.content = content;
    this.lineWrap = lineWrap;
    this.lineCount = lineCount;
    if(lineWrap){
      try {
        wrapChildren();
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("构造扩展内容出现异常");
      }
    }else {
      int tempCount = CharsetCount.count(content);
      int result =  lineCount -tempCount;
      this.content = addBlank(content,result);
    }
  }

  private void wrapChildren() throws Exception{
    if(realLen <= lineCount){
      System.out.println("当前:x->"+row+" y->"+cell+"数据："+content+"长度小于列宽，不进行自动换行");
      return;
    }
    System.out.println("当前:x->"+row+" y->"+cell+"数据："+content+"开始封装处理");
    int childCount = 0;
    int startIndex = 0;
    int endIndex = 0;
    boolean finishFlag = true;
    while(finishFlag){
      endIndex = (++childCount) * lineCount ;
      if( endIndex >= javaLen){
        endIndex = javaLen;
      }
      String tempContext = "";
      int temCount = 0;
      int result = 0;
      while(true){
        tempContext = content.substring(startIndex,endIndex);
        temCount = CharsetCount.count(tempContext);
        result =  temCount - lineCount;
        if(result <= 0) {
          break;
        }
        endIndex = endIndex - 1;
      };
      tempContext = addBlank(tempContext,result);
      //如果截取完长度不足，进行换行处理
     if(endIndex == javaLen){
        finishFlag = false;
        result =   lineCount -temCount;
        tempContext = addBlank(tempContext,result);
      }
      CellVo cellVo = new CellVo(row,cell,new String(tempContext),lineCount,false);
      childList.add(cellVo);
      startIndex = endIndex;
      System.out.println("封装扩充数据"+cellVo+"成功");
    }
    System.out.println("封装扩充数据结束");
  }

  public void completeChild(int completeNum){
    int count = 0;
    //没有孩子节点的话，需要将自身给加入进来，并且需要补充不足的长度
    if(getChildCount() == 0){
      int temCount = CharsetCount.count(content);
      int result = lineCount - temCount;
      String tempContext = "";
      if(result > 0 ) {
        tempContext = addBlank(content,result);
      }else {
        tempContext = content;
      }
      CellVo cellVo = new CellVo(row,cell,tempContext,lineCount,false);
      childList.add(cellVo);
      count = count+1;
    }
    if(completeNum == 0 ){
      System.out.println("只需补全自己即可"+content);
      return;
    }
    for (; count < completeNum; count++){
      String context = addBlank("",lineCount);
      CellVo cellVo = new CellVo(row,cell,context,lineCount,false);
      childList.add(cellVo);
    }
  }

  private String addBlank(String context, int count){
    StringBuilder contextBuilder = new StringBuilder(context);
    for (int i = 0; i < count; i++) {
      contextBuilder.append(" ");
    }
    return contextBuilder.toString();
  }

  public int getJavaLen() {
    return javaLen;
  }

  public void setJavaLen(int javaLen) {
    this.javaLen = javaLen;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getCell() {
    return cell;
  }

  public void setCell(int cell) {
    this.cell = cell;
  }

  public int getRealLen() {
    return realLen;
  }

  public void setRealLen(int realLen) {
    this.realLen = realLen;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<CellVo> getChildList() {
    return childList;
  }

  public int getChildCount() {
    return childList.size();
  }


  @Override
  public String toString() {
    return "数据信息{" +
        "行(x)" + row +
        ", 列(y)" + cell +
        ", 真实长度=" + realLen +
        ", java长度=" + javaLen +
        ", 内容='" + content + '\'' +
        '}';
  }
}
