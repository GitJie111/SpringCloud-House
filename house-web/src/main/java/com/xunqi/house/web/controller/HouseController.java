package com.xunqi.house.web.controller;

import com.xunqi.house.biz.service.HouseService;
import com.xunqi.house.common.page.PageData;
import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.House;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-02 08:43
 **/
@Controller
public class HouseController {

    @Resource
    private HouseService houseService;

    /**
     * 1.实现分页
     * 2.支持小区搜索、类型搜索
     * 3.支持排序
     * 4.支持展示图片、价格、标题、地址等信息
     * @return
     */
    @RequestMapping(value = "/house/list")
    public String houseList(Integer pageSize, Integer pageNumber, House query, ModelMap modelMap) {

        PageData<House> housePageData = houseService.queryHouse(query, PageParams.build(pageSize, pageNumber));

        modelMap.put("ps",housePageData);
        modelMap.put("vo",query);

        return "house/listing";
    }

}
