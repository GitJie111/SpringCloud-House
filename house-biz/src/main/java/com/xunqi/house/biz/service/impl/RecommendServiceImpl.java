package com.xunqi.house.biz.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.xunqi.house.biz.service.HouseService;
import com.xunqi.house.biz.service.RecommendService;
import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.House;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-08 10:35
 **/
@Service("recommendService")
public class RecommendServiceImpl implements RecommendService {

    private static final String HOT_HOUSE_KEY = "hot_house";

    @Resource
    private HouseService houseService;


    @Override
    public void increase(Long id) {
        Jedis jedis = new Jedis("localhost");
        jedis.auth("123456");
        jedis.zincrby(HOT_HOUSE_KEY,1.0D,id + "");
        jedis.zremrangeByRank(HOT_HOUSE_KEY,10,-1);
        jedis.close();
    }

    @Override
    public List<Long> getHot() {
        Jedis jedis = new Jedis("localhost");
        jedis.auth("123456");

        Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY, 0, -1);
        jedis.close();

        List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
        return ids;
    }

    @Override
    public List<House> getHotHouse(Integer size) {
        House query = new House();
        List<Long> list = getHot();
        list = list.subList(0, Math.min(list.size(), size));
        if (list.isEmpty()) {
            return Lists.newArrayList();
        }

        query.setIds(list);
        final List<Long> order = list;

        //查询头像图片地址
        List<House> houses = houseService.queryAndSetImg(query, new PageParams(size, 1));

        //进行排序
        Ordering<House> houseSort = Ordering.natural().onResultOf(hs -> {
            return order.indexOf(hs.getId());
        });

        return houseSort.sortedCopy(houses);
    }


}
