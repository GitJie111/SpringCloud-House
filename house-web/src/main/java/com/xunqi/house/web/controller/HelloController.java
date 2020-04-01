package com.xunqi.house.web.controller;

import com.xunqi.house.biz.service.UserService;
import com.xunqi.house.common.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
        if (user != null) {
            throw new IllegalArgumentException();
        }
        modelMap.put("user",user);
        return "hello";
    }

    @RequestMapping("/index")
    public String index() {
        return "homepage/index";
    }
}
