package com.xunqi.house.biz.service.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.xunqi.house.biz.mapper.HouseMapper;
import com.xunqi.house.biz.service.AgentService;
import com.xunqi.house.biz.service.FileService;
import com.xunqi.house.biz.service.HouseService;
import com.xunqi.house.biz.service.MailService;
import com.xunqi.house.common.enums.HouseUserType;
import com.xunqi.house.common.page.PageData;
import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.*;
import com.xunqi.house.common.util.BeanHelper;
import org.apache.commons.collections.CollectionUtils;
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

    @Resource
    private FileService fileService;

    @Resource
    private AgentService agentService;

    @Resource
    private MailService mailService;

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


    @Override
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


    @Override
    public House queryOneHouse(Long id) {
        House query = new House();
        query.setId(id);
        //查询房屋信息，传入分页属性显示一条数据
        List<House> houses = queryAndSetImg(query,PageParams.build(1,1));

        //如果不为空则返回数据
        if (!houses.isEmpty()) {
            return houses.get(0);
        }

        return null;
    }

    @Override
    public void addUserMsg(UserMsg userMsg) {

        BeanHelper.onInsert(userMsg);

        houseMapper.insertUserMsg(userMsg);

        User agent = agentService.getAgentDetail(userMsg.getAgentId());

        mailService.sendMail("来自用户" + userMsg.getEmail() + "的留言"
                ,userMsg.getMsg(),agent.getEmail());

    }

    @Override
    public HouseUser getHouseUser(Long houseId){
        HouseUser houseUser =  houseMapper.selectSaleHouseUser(houseId);
        return houseUser;
    }

    @Override
    public List<Community> getAllCommunitys() {
        Community community = new Community();
        List<Community> communities = houseMapper.selectCommunity(community);
        return communities;
    }

    @Override
    public void addHouse(House house, User user) {
        //判断是否传来房产图片信息
        if (CollectionUtils.isNotEmpty(house.getHouseFiles())) {
            String images = Joiner.on(",")
                    .join(fileService.getImgPaths(house.getHouseFiles()));
            house.setImages(images);
        }
        if (CollectionUtils.isNotEmpty(house.getFloorPlanFiles())) {
            String images = Joiner.on(",").join(fileService.getImgPaths(house.getFloorPlanFiles()));
            house.setFloorPlan(images);
        }
        BeanHelper.onInsert(house);
        houseMapper.insert(house);
        bindUser2House(house.getId(),user.getId(),false);
    }


    @Override
    public void bindUser2House(Long houseId, Long userId, boolean isCollect) {
        HouseUser existHouseUser = houseMapper.selectHouseUser(
                userId,houseId,isCollect ? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value);

        if (existHouseUser != null) {
            return;
        }
        HouseUser houseUser = new HouseUser();
        houseUser.setHouseId(houseId);
        houseUser.setUserId(userId);
        houseUser.setType(isCollect ? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value);
        BeanHelper.setDefaultProp(houseUser,HouseUser.class);
        BeanHelper.onInsert(houseUser);
        houseMapper.insertHouseUser(houseUser);
    }


    @Override
    public void updateRating(Long id, Double rating) {
        //先查询出指定数据
        House house = queryOneHouse(id);
        //获取到原来的星级
        Double oldRating = house.getRating();
        //计算平均星级,不允许评论的星级大于5
        Double newRating = oldRating.equals(0D) ? rating : Math.min((oldRating + rating)/2,5);

        House updateHouse = new House();
        updateHouse.setId(id);
        updateHouse.setRating(newRating);

        //执行更新操作
        BeanHelper.onUpdate(updateHouse);
        houseMapper.updateHouse(updateHouse);
    }

    @Override
    public void unbindUser2House(Long id, Long userId, HouseUserType type) {
        if (type.equals(HouseUserType.SALE)) {
            houseMapper.downHouse(id);
        } else {
            houseMapper.deleteHouseUser(id, userId, type.value);
        }
    }
}
