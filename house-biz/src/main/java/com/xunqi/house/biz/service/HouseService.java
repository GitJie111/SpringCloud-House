package com.xunqi.house.biz.service;

import com.xunqi.house.common.page.PageData;
import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.House;

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
}
