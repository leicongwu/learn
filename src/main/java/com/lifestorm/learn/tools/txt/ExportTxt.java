package com.lifestorm.learn.tools.txt;


import com.lifestorm.learn.tools.txt.vo.TableVo;
import com.lifestorm.learn.tools.txt.vo.TxtConfig;
import com.lifestorm.learn.vo.TempVo;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * Created by leicongwu on 2019/9/9.
 */
public class ExportTxt {

  public static void main(String[] args) {
    ExportTxt exportTxt = new ExportTxt();
//    exportTxt.exportTxtDemo();
    exportTxt.autoCovery();
  }

  public void autoCovery(){
    TempVo tempVo = new TempVo();
    tempVo.setEndDate(new Date());
    tempVo.setName("这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字这是一个名字");
    tempVo.setAge(10);
    tempVo.setMoney(110001111111111111L);
    tempVo.setTimes(100000.02d);
    TempVo tempVo2 = new TempVo();
    tempVo2.setEndDate(new Date());
    tempVo2.setName("这是2个名字这名字这是2个名字这是2个名字这是2个名字这是名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字这是2个名字");
    tempVo2.setAge(100);
    tempVo2.setMoney(1200000000000000000L);
    tempVo2.setTimes(11.0);
    List<TempVo> tempVos = new ArrayList<TempVo>();
    tempVos.add(tempVo);
    tempVos.add(tempVo2);
    String[] titleName = new String[]{"姓名","钱","次数","年龄"};
    String[] fileldName = new String[]{"name","money","times","age"};
   /* Map<Integer,List<String>> contextMap = transferBizListToMap(tempVos,TempVo.class,fileldName,titleName);
    int MAX_LEN = 20*2;//实际中文字符长度
    int averLen = (int)Math.floor(MAX_LEN/fileldName.length);
    System.out.println("当前定义长度为"+MAX_LEN+"，共有"+fileldName.length+"行需要进行展示，当前计算得出列宽为"+averLen);
    transformCells(contextMap,averLen);*/
    exportTxt(new TxtConfig(tempVos, TempVo.class, fileldName, titleName, null, 50));
  }

  public void exportTxtDemo(){
    List<String> titleList = new ArrayList<String>();
    titleList.add("品名");
    titleList.add("产地");
    titleList.add("申请数量");
    titleList.add("申请重量");
    titleList.add("生产日期");
    List<String> row1 = new ArrayList<String>();
    row1.add("牛肉牛肉牛肉123");
    row1.add("中国");
    row1.add("2件");
    row1.add("100 kg");
    row1.add("日期为我们想要的1");
    List<String> row2 = new ArrayList<String>();
    row2.add("羊肉");
    row2.add("法国");
    row2.add("1 件");
    row2.add("1000000000000000 kg");
    row2.add("日期为我们想要的2");
    List<String> row3 = new ArrayList<String>();
    row3.add("羊肉");
    row3.add("德国");
    row3.add("10件");
    row3.add("10kg");
    row3.add("日期为我们想要的3");
    Map<Integer,List<String>> contextMap = new HashMap<Integer,List<String>>();
    contextMap.put(0,titleList);
    contextMap.put(1,row1);
    contextMap.put(2,row2);
    contextMap.put(3,row3);
    transformCells(contextMap,6);

  }


  /**
   *  导出数据集合为txt类型.
   * @param <T>
   * @param txtConfig
   */
  public <T>void exportTxt(TxtConfig txtConfig){
    Map<Integer,List<String>> contextMap = transferBizListToMap(txtConfig.getObjList(),
        txtConfig.getCls(),
        txtConfig.getColumnName(),
        txtConfig.getTitleName(),
        txtConfig.getFormat());
    if( contextMap ==null){
      System.out.println("数据为空");
      return;
    }
    if(txtConfig.getChineseLen() <= 0 ){
      txtConfig.setChineseLen(50);
    }
    int MAX_LEN = txtConfig.getChineseLen() *2;//实际中文字符长度
    int averLen = (int)Math.floor(MAX_LEN/ txtConfig.getColumnName().length);
    System.out.println("当前定义长度为"+MAX_LEN+"，共有"+ txtConfig.getColumnName().length+"行需要进行展示，"
        + "当前计算得出列宽为"+averLen);
    transformCells(contextMap,averLen);
  }

  /**
   * 进行数据转换
   * @param objList    数据列表
   * @param cls        数据类型
   * @param columnName 展示的列名
   * @param titleName 标题名称
   * @param <T>
   * @return
   */
  private <T> Map<Integer, List<String>> transferBizListToMap(List<T> objList, Class<T> cls,
      String[] columnName, String[] titleName) {
    return transferBizListToMap(objList, cls, columnName,titleName, null);
  }


  /**
   *
   * @param objList 数据对象列表
   * @param cls   数据类型
   * @param columnName 显示的列名
   * @param titleName 标题
   * @param format 日期的格式，默认为yyyy-MM-dd HH:mm:ss
   * @param <T>
   * @return
   */
  private <T> Map<Integer, List<String>> transferBizListToMap(List<T> objList, Class<T> cls,
      String[] columnName,String[] titleName, String format) {
    if(columnName == null || titleName == null){
      System.out.println("展示的列名、标题名称不能为空");
      return null;
    }
    if( columnName.length != titleName.length){
      System.out.println("展示的列名与标题个数必须一致");
      return null;
    }
    if (CollectionUtils.isEmpty(objList)) {
      System.out.println("数据不能为空");
      return null;
    }

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    if (StringUtils.isNotEmpty(format)) {
      try {
        dateTimeFormatter = DateTimeFormatter.ofPattern(format);
      } catch (Exception ex) {
        System.err.println("输入的日期格式化格式有问题，将使用默认的yyyy-MM-dd HH:mm:ss方式进行格式化");
      }
    }
    Field[] fields = cls.getDeclaredFields();
    Map<String, Field> fieldMap = new HashMap<String, Field>();
    for (Field columnField : fields) {
      columnField.setAccessible(true);
      String name = columnField.getName();
      fieldMap.put(name, columnField);
    }

    int size = objList.size();
    Map<Integer, List<String>> objMap = new HashMap<Integer, List<String>>(size);
    List<String> titleList = Arrays.asList(titleName);
    objMap.put(0,titleList);
    for (int i = 0; i < size; i++) {
      T bizObj = objList.get(i);
      List<String> sList = new ArrayList<String>(columnName.length);
      for (String showColumn : columnName) {
        Field field = fieldMap.get(showColumn);
        if (field == null) {
          sList.add("");
          continue;
        }
        Object value = null;
        try {
          value = field.get(bizObj);
          if (value == null) {
            sList.add("");
            continue;
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
        Class type = field.getType();
        try {
          if (type == Date.class) {
            String dateValueStr = "";
            Date dateValue = null;
            dateValue = (Date) value;
            LocalDateTime localDate = LocalDateTime
                .ofInstant(dateValue.toInstant(), ZoneId.systemDefault());
            dateValueStr = localDate.format(dateTimeFormatter);
            sList.add(dateValueStr);
          } else {
            String valueStr = "";
            valueStr = value.toString();
            sList.add(valueStr);
          }
        } catch (Exception ex) {
          System.out.println("进行数据转换时出现异常了");
          sList.add("");
        }
      }
      objMap.put(i+1, sList);
    }
    return objMap;
  }

  /**
   *
   * @param rowMap
   * @param maxLen 最大列宽
   */
  private void transformCells(Map<Integer,List<String>> rowMap, int maxLen){
    TableVo tableVo = new TableVo();
    Set<Integer> keySet = rowMap.keySet();
    Iterator<Integer> iterator = keySet.iterator();
    while(iterator.hasNext()){
      Integer rowNum = iterator.next();
      List<String> rowList = rowMap.get(rowNum);
      tableVo.addList(rowList,rowNum,maxLen);
    }
    String txtShow = tableVo.wrapTxt();
    System.out.println("输出的文本为：");
    System.out.println(txtShow);
    tableVo.exportToFile(txtShow.toString());
  }

}
