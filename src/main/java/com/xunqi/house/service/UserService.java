package com.xunqi.house.service;

import com.xunqi.house.pojo.User;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-25 09:31
 **/
public interface UserService {

    /**
     * 查询全部用户
     * @return
     */
    List<User> findAll();

}
