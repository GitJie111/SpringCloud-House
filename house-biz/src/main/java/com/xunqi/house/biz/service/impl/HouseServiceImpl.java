package com.xunqi.house.biz.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.xunqi.house.biz.mapper.HouseMapper;
import com.xunqi.house.biz.service.HouseService;
import com.xunqi.house.common.page.PageData;
import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.Community;
import com.xunqi.house.common.pojo.House;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-02 08:44
 **/
@Service("houseService")
public class HouseServiceImpl implements HouseService {

    @Value("${file.prefix}")
    private String imgPrefix;

    @Resource
    private HouseMapper houseMapper;

    /**
     * 1.查询小区
     * 2.添加图片服务地址前缀
     * 3.构建分页结果
     * @param query
     * @param pageParams
     * @return
     */
    @Override
    public PageData<House> queryHouse(House query, PageParams pageParams) {

        List<House> houses = Lists.newArrayList();

        //条件查询
        if (!Strings.isNullOrEmpty(query.getName())) {
            Community community = new Community();
            community.setName(query.getName());

            //查询小区信息
            List<Community> communities = houseMapper.selectCommunity(community);

            //如果查询出来的小区信息不为空，就设置id
            if (!communities.isEmpty()) {
                query.setCommunityId(communities.get(0).getId());
            }
        }
        houses = queryAndSetImg(query,pageParams);
        //查询总数量
        Long count = houseMapper.selectPageCount(query);

        return PageData.buildPage(houses,count,pageParams.getPageSize(),pageParams.getPageNum());
    }

    public List<House> queryAndSetImg(House query, PageParams pageParams) {
        List<House> houses = houseMapper.selectPageHouses(query, pageParams);
        houses.forEach(h ->{
            h.setFirstImg(imgPrefix + h.getFirstImg());

            h.setImageList(h.getImageList().stream().map(img -> imgPrefix + img)
                    .collect(Collectors.toList()));

            h.setFloorPlanList(h.getFloorPlanList().stream().map(img -> imgPrefix + img)
                    .collect(Collectors.toList()));
        });
        return houses;
    }
}
