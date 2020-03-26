package com.xunqi.house.controller;

import com.xunqi.house.pojo.User;
import com.xunqi.house.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-25 09:51
 **/
@Controller
public class HelloController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/hello")
    public String hello(ModelMap modelMap) {
        List<User> users = userService.findAll();
        User user = users.get(0);
        modelMap.put("user",user);
        return "hello";
    }

}
