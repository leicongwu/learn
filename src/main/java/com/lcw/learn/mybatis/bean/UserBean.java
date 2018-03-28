package com.lcw.learn.mybatis.bean;

import java.io.Serializable;

/**
 * Created by leicongwu on 2018/3/19.
 */
public class UserBean implements Serializable{
    private static final long serizalVersionUID = 1l;
    private Integer id;
    private String username;
    private String password ;
    private Double account;

    public UserBean(String username, String password, Double account) {
        this.username = username;
        this.password = password;
        this.account = account;
    }

    public UserBean(Integer id, String username, String password, Double account) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }
}
