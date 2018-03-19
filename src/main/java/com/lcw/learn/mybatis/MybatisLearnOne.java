package com.lcw.learn.mybatis;

import org.apache.ibatis.session.SqlSession;

/**
 * Created by leicongwu on 2018/3/17.
 */
public class MybatisLearnOne {

    public static void main(String[] args) {
        insertUser();
    }

    private static void insertUser(){
        SqlSession session = DBTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        UserBean userBean = new UserBean("username","pasword",700.0);
        try {
            mapper.insertUser(userBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
