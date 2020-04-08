package com.xunqi.house.web.controller;

import com.xunqi.house.biz.service.AgentService;
import com.xunqi.house.biz.service.HouseService;
import com.xunqi.house.biz.service.RecommendService;
import com.xunqi.house.common.constants.CommonConstants;
import com.xunqi.house.common.page.PageData;
import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.House;
import com.xunqi.house.common.pojo.HouseUser;
import com.xunqi.house.common.pojo.UserMsg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-02 08:43
 **/
@Controller
public class HouseController {

    @Resource
    private HouseService houseService;

    @Resource
    private AgentService agentService;

    @Resource
    private RecommendService recommendService;

    /**
     * 1.实现分页
     * 2.支持小区搜索、类型搜索
     * 3.支持排序
     * 4.支持展示图片、价格、标题、地址等信息
     * @return
     */
    @RequestMapping(value = "/house/list")
    public String houseList(Integer pageSize, Integer pageNumber, House query, ModelMap modelMap) {
        //查询分页房产信息
        PageData<House> housePageData = houseService.queryHouse(query, PageParams.build(pageSize, pageNumber));
        //查询热门房产
        List<House> hotHouses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses",hotHouses);
        modelMap.put("ps",housePageData);
        modelMap.put("vo",query);

        return "house/listing";
    }


    /**
     * 查询房屋详情
     * 查询关联经纪人
     * @param id
     * @return
     */
    @RequestMapping(value = "/house/detail")
    public String houseDetail(Long id,ModelMap modelMap) {
        House house = houseService.queryOneHouse(id);

        HouseUser houseUser = houseService.getHouseUser(id);
        recommendService.increase(id);
        if (houseUser.getUserId() != null && !houseUser.getUserId().equals(0)) {
            modelMap.put("agent",agentService.getAgentDetail(houseUser.getUserId()));
        }

        //查询热门房产
        List<House> hotHouses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses",hotHouses);

        modelMap.put("house",house);

        return "house/detail";
    }


    /**
     * 用户留言
     * @param userMsg
     * @return
     */
    @RequestMapping(value = "/house/leaveMsg")
    public String houseMsg(UserMsg userMsg) {

        houseService.addUserMsg(userMsg);

        return "redirect:/house/detail?id=" + userMsg.getHouseId();
    }

}
