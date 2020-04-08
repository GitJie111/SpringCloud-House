package com.xunqi.house.biz.mapper;

import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-02 09:07
 **/
@Mapper
public interface HouseMapper {


    /**
     * 分页多条件查询房屋信息
     * @param house
     * @param pageParams
     * @return
     */
    public List<House> selectPageHouses(@Param("house") House house,
                                        @Param("pageParams") PageParams pageParams);

    /**
     * 查询房屋数量
     * @param query
     * @return
     */
    public Long selectPageCount(@Param("house") House query);


    /**
     * 添加房屋信息
     * @param house
     * @return
     */
    public int insert(House house);

    /**
     * 查询小区信息
     * @param community
     * @return
     */
    List<Community> selectCommunity(Community community);


    /**
     * 新增信息
     * @param userMsg
     * @return
     */
    int insertUserMsg(UserMsg userMsg);
    
    public HouseUser selectSaleHouseUser(@Param("id") Long houseId);

    /**
     * 查询房屋关联用户
     * @param userId
     * @param houseId
     * @param type
     * @return
     */
    HouseUser selectHouseUser(@Param("userId") Long userId,
                              @Param("houseId")Long houseId,
                              @Param("type") Integer type);

    /**
     * 新增房屋用户信息表
     * @param houseUser
     * @return
     */
    int insertHouseUser(HouseUser houseUser);
}
