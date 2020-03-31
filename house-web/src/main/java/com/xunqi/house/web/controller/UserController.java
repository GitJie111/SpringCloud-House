package com.xunqi.house.web.controller;

import com.xunqi.house.biz.service.UserService;
import com.xunqi.house.common.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-25 09:32
 **/
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/")
    public List<User> list() {
        return userService.findAll();
    }

}
