package com.lcw.learn.mybatis;

import java.util.List;

/**
 * Created by leicongwu on 2018/3/19.
 */
public interface UserMapper {

    public int insertUser(UserBean userBean) throws Exception ;

    public int updateUser(UserBean user,int id) throws Exception ;

    public int deleteUser(int id) throws Exception ;

    public UserBean selectUserById(int id) throws Exception ;

    public List<UserBean> selectAllUser() throws Exception;


}
