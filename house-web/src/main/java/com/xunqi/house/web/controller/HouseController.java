package com.xunqi.house.web.controller;

import com.xunqi.house.biz.service.AgentService;
import com.xunqi.house.biz.service.CityService;
import com.xunqi.house.biz.service.HouseService;
import com.xunqi.house.biz.service.RecommendService;
import com.xunqi.house.common.constants.CommonConstants;
import com.xunqi.house.common.enums.HouseUserType;
import com.xunqi.house.common.page.PageData;
import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.*;
import com.xunqi.house.common.result.ResultMsg;
import com.xunqi.house.web.interceptor.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Resource
    private CityService cityService;

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


    @RequestMapping(value = "/house/toAdd")
    public String toAdd(ModelMap modelMap) {
        List<City> allCitys = cityService.getAllCitys();
        List<Community> allCommunitys = houseService.getAllCommunitys();
        modelMap.put("citys",allCitys);
        modelMap.put("communitys",allCommunitys);

        return "house/add";
    }

    /**
     * 获取用户
     * 设置房产状态（上线）
     * 添加房产
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/house/add")
    public String doAdd(House house,ModelMap modelMap) {
        //获取用户信息
        User user = UserContext.getUser();

        //设置房产状态
        house.setState(CommonConstants.HOUSE_STATE_UP);

        //添加房产信息
        houseService.addHouse(house,user);
        return "redirect:/house/ownlist";
    }

    @RequestMapping(value = "/house/ownlist")
    public String ownlist(House house,
                          @RequestParam(required = false,defaultValue = "3") Integer pageNum,
                          @RequestParam(required = false,defaultValue = "1") Integer pageSize, ModelMap modelMap) {
        User user = UserContext.getUser();
        house.setUserId(house.getId());
        house.setBookmarked(false);
        modelMap.put("ps",houseService.queryHouse(house,PageParams.build(pageSize,pageNum)));
        modelMap.put("pageType","own");
        return "house/ownlist";

    }


    //1.评分
    @ResponseBody
    @RequestMapping(value = "/house/rating")
    public ResultMsg houseRate(Double rating,Long id) {
        houseService.updateRating(id,rating);
        return ResultMsg.successMsg("ok");
    }


    //2.收藏
    @ResponseBody
    @RequestMapping(value = "/house/bookmark")
    public ResultMsg bookmark(Long id) {
        User user = UserContext.getUser();
        houseService.bindUser2House(id,user.getId(),true);
        return ResultMsg.successMsg("ok");
    }

    //3.删除收藏
    @ResponseBody
    @RequestMapping(value = "/house/unbookmark")
    public ResultMsg unbookmark(Long id) {
        User user = UserContext.getUser();
        houseService.unbindUser2House(id,user.getId(), HouseUserType.BOOKMARK);
        return ResultMsg.successMsg("ok");
    }

    @RequestMapping(value = "/house/del")
    public String delSale(Long id,String pageType) {
        User user = UserContext.getUser();
        houseService.unbindUser2House(id,
                user.getId(),
                pageType.equals("own") ? HouseUserType.SALE : HouseUserType.BOOKMARK);

        return "redirect:/house/ownlist";
    }


    //4.收藏列表
    @RequestMapping(value = "/house/bookmarked")
    public String bookmarked(House house,
                             @RequestParam(required = false,defaultValue = "3") Integer pageNum,
                             @RequestParam(required = false,defaultValue = "1") Integer pageSize,
                             ModelMap modelMap) {

        User user = UserContext.getUser();
        house.setBookmarked(true);
        house.setUserId(user.getId());
        PageData<House> housePageData = houseService.queryHouse(house, PageParams.build(pageSize, pageNum));
        modelMap.put("ps",housePageData);
        modelMap.put("pageType","book");

        return "house/ownlist";
    }


}
