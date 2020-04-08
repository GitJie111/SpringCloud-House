package com.xunqi.house.web.controller;

import com.xunqi.house.biz.service.RecommendService;
import com.xunqi.house.common.pojo.House;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-08 11:39
 **/
@Controller
public class HomePageController {

    @Resource
    private RecommendService recommendService;

    @RequestMapping(value = "/index")
    public String index(ModelMap modelMap) {
        List<House> houses = recommendService.getLastest();
        modelMap.put("recomHouses",houses);
        return "homepage/index";
    }

    @RequestMapping(value = "")
    public String home(ModelMap modelMap) {
        return "redirect:/index";
    }

}
