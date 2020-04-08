package com.xunqi.house.biz.service.impl;

import com.google.common.collect.Lists;
import com.xunqi.house.biz.service.CityService;
import com.xunqi.house.common.pojo.City;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-08 14:58
 **/
@Service("cityService")
public class CityServiceImpl implements CityService {


    @Override
    public List<City> getAllCitys() {
        City city = new City();
        city.setCityCode("110000");
        city.setCityName("北京");
        city.setId(1);
        return Lists.newArrayList(city);
    }
}
