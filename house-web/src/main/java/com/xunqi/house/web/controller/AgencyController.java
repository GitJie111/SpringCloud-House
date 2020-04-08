package com.xunqi.house.web.controller;

import com.xunqi.house.biz.service.AgentService;
import com.xunqi.house.biz.service.HouseService;
import com.xunqi.house.common.page.PageData;
import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.House;
import com.xunqi.house.common.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-08 09:15
 **/
@Controller
public class AgencyController {

    @Resource
    private AgentService agentService;

    @Resource
    private HouseService houseService;

    @RequestMapping("/agency/agentList")
    public String agentList(@RequestParam(required = false,defaultValue = "2") Integer pageSize,
                            @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                            ModelMap modelMap) {

        PageData<User> ps = agentService.getAllAgent(PageParams.build(pageSize,pageNum));

        modelMap.put("ps",ps);

        return "user/agent/agentList";
    }


    @RequestMapping("/agency/agentDetail")
    public String agentDetail(Long id,ModelMap modelMap) {
        //查询经纪人详情
        User agentDetail = agentService.getAgentDetail(id);

        //设置属性
        House query = new House();
        query.setUserId(id);
        query.setBookmarked(false);

        //分页查询房屋列表信息
        PageData<House> housePageData = houseService.queryHouse(query, new PageParams(3, 1));

        if (housePageData != null) {
            modelMap.put("bindHouses",housePageData.getList());
        }

        modelMap.put("agent",agentDetail);

        return "user/agent/agentDetail";
    }

}
