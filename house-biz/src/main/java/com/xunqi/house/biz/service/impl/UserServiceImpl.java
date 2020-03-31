package com.xunqi.house.biz.service.impl;

import com.xunqi.house.biz.mapper.UserMapper;
import com.xunqi.house.biz.service.UserService;
import com.xunqi.house.common.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-25 09:31
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
