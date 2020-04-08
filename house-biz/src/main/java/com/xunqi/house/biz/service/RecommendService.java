package com.xunqi.house.biz.service;

import com.xunqi.house.common.pojo.House;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-08 10:34
 **/
public interface RecommendService {

    /**
     * 增加
     * @param id
     */
    public void increase(Long id);

    public List<Long> getHot();

    public List<House> getHotHouse(Integer size);

}
