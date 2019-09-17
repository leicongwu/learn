package com.lifestorm.learn.vo;


import java.util.Date;

/**
 * Created by engle on 2019/9/17.
 */
public class TempVo {

  private Date endDate;

  private String name;

  private int age;

  private long money;

  private double times;

  private String index;

  public TempVo(){}

  public TempVo(Date endDate, String name, int age, long money, double times, String index) {
    this.endDate = endDate;
    this.name = name;
    this.age = age;
    this.money = money;
    this.times = times;
    this.index = index;
  }

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public long getMoney() {
    return money;
  }

  public void setMoney(long money) {
    this.money = money;
  }

  public double getTimes() {
    return times;
  }

  public void setTimes(double times) {
    this.times = times;
  }
}
