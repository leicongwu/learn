package com.lcw.learn.mybatis;

import com.lcw.learn.mybatis.bean.UserBean;
import com.lcw.learn.mybatis.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by leicongwu on 2018/3/17.
 */
public class MybatisLearnOne {
    public static void main(String[] args) {
//        insertUser();
//        deleteUser();
//        selectUserById();
//        selectAllUser();
        updateUserById();
    }

    /**
     *新增用户
     */
    private static void insertUser(){
        SqlSession session = DBTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        UserBean userBean = new UserBean(9,"username","pasword",800.0);
        try {
            mapper.insertUser(userBean);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    /**
     * 删除用户
     */
    public static void deleteUser(){
        SqlSession session = DBTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        try{
            mapper.deleteUser(2);
            session.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            session.rollback();
        }

    }

    /**
     * 根据用户ID更新用户表信息
     */
    public static void updateUserById(){
        SqlSession session = DBTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        try {
            UserBean userBean = new UserBean("user","pse",1000d);
            mapper.updateUser(userBean,3);
            session.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            session.rollback();
        }
    }


    /**
     * 根据用户ID查询
     */
    public static void selectUserById(){
        SqlSession session = DBTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        try{
            UserBean userBean = mapper.selectUserById(3);
            System.out.println(userBean.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 查询所有的用户
     */
    public static void selectAllUser(){
        SqlSession session = DBTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        try {
            List<UserBean> uList =  mapper.selectAllUser();
            System.out.println(uList.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
