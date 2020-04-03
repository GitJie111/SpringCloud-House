package com.xunqi.house.biz.service;

import com.xunqi.house.common.page.PageData;
import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.House;
import com.xunqi.house.common.pojo.HouseUser;
import com.xunqi.house.common.pojo.UserMsg;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-02 08:44
 **/
public interface HouseService {


    /**
     * 分页多条件查询房屋列表
     * @param query
     * @param pageParams
     * @return
     */
    PageData<House> queryHouse(House query, PageParams pageParams);

    /**
     * 根据id查询房屋信息
     * @param id
     * @return
     */
    House queryOneHouse(Long id);

    /**
     * 添加用户留言信息
     * @param userMsg
     */
    void addUserMsg(UserMsg userMsg);

    public HouseUser getHouseUser(Long houseId);
}
