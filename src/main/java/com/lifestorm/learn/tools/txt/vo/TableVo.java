package com.lifestorm.learn.tools.txt.vo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.springframework.util.CollectionUtils;

/**
 * Created by leicongwu on 2019/9/9.
 */
public class TableVo {

  List<List<CellVo>> cellVoList = new ArrayList<List<CellVo>>();

  /**
   * 封装单元格
   * @param rowList
   * @param numLine
   */
  public void addList(List<String> rowList, int numLine,int maxLength){
    int count = rowList.size();
    List<CellVo> cellList = new ArrayList<CellVo>(count);
    for (int i = 0; i < count; i++) {
      String context = rowList.get(i);
      CellVo cellVo = new CellVo(numLine,i,context,maxLength,true);
      cellList.add(cellVo);
    }
    cellVoList.add(cellList);
  }

  /**
   * 展示
   */
  public String wrapTxt(){
    StringBuilder showContext = new StringBuilder();
    int totalRow = cellVoList.size();
    //序号
    for (int row = 0; row < totalRow; row++){
      List<CellVo> cellList = cellVoList.get(row);
      int totalCell = cellList.size();
      int maxChildCount = childrenMax(cellList);
      System.out.println(row+"有"+maxChildCount+"个孩子节点");
      //补充子节点数据项
      completeCells(cellList,maxChildCount);
      //对数据进行封装，同一行的放在一起进行顺序输出
      Map<Integer,List<CellVo>> tempMap = new HashMap<Integer,List<CellVo>>();
      for (int cell = 0; cell < totalCell; cell++) {
        CellVo cellVo = cellList.get(cell);
        List<CellVo> cList = cellVo.getChildList();
        int childSize = cList.size();
        for (int i = 0; i < childSize; i++) {
          CellVo cellTemp = cList.get(i);
          List<CellVo> vList = tempMap.get(i);
          if (CollectionUtils.isEmpty(vList)) {
            vList = new ArrayList<CellVo>(childSize);
            tempMap.put(i, vList);
          }
          vList.add(cellTemp);
        }
      }
      //输出样式
      if( maxChildCount == 0){
        //针对孩子节点为0的情况，补充完成之后，最大节点数为1.
        maxChildCount = 1;
      }
      for (int i=0; i < maxChildCount; i++){
        List<CellVo> vList = tempMap.get(i);
        for (CellVo vo : vList){
          showContext.append(vo.getContent());
          showContext.append("     ");
        }
        showContext.append("\r\n");
      }

    }
    return showContext.toString();
  }

  public void exportToFile(String showContext){
    File file = new File("C:\\","temp.txt");
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter(file);
      IOUtils.write(showContext,fileWriter);
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      IOUtils.closeQuietly(fileWriter);
    }
  }

  public void completeCells(List<CellVo> cellList, int max){
    int totalCell = cellList.size();
    for (int cell =0 ; cell < totalCell; cell++){
      CellVo cellVo = cellList.get(cell);
      int nowCount = cellVo.getChildCount();
      int result = max - nowCount;
      cellVo.completeChild(result);
    }
  }

  /**
   *取得各个孩子节点最大的数据量
   * @param cellList
   * @return
   */
  public int childrenMax(List<CellVo> cellList){
    int totalCell = cellList.size();
    int max = 0;
    for (int cell =0 ; cell < totalCell; cell++){
      CellVo cellVo = cellList.get(cell);
      int nowCount = cellVo.getChildCount();
      if( nowCount > max){
        max = nowCount;
      }
    }
    return max;
  }

}
