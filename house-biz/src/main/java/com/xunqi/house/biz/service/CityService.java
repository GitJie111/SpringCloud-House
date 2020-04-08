package com.xunqi.house.biz.service;

import com.xunqi.house.common.pojo.City;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-08 14:58
 **/
public interface CityService {

    /**
     * 查询所有小区信息
     * @return
     */
    public List<City> getAllCitys();

}
