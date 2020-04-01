package com.xunqi.house.biz.service;

import com.xunqi.house.common.pojo.User;

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

    /**
     * 1.插入数据库，非激活；密码加盐MD5；保存头像到本地
     * 2.生成key，绑定Email
     * 3.发送邮件给客户
     * @return
     */
    boolean addAccount(User account);

    /**
     * 将用户的激活状态改为true
     * @param key
     * @return
     */
    boolean enable(String key);

    /**
     * 用户名和密码验证
     * @param username
     * @param password
     * @return
     */
    User auth(String username, String password);

    /**
     * 修改用户信息
     * @param updateUser
     * @param email
     * @return
     */
    int updateUser(User updateUser, String email);

    List<User> getUserByQuery(User query);
}
