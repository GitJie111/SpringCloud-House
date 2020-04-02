package com.xunqi.house.biz.mapper;

import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.Community;
import com.xunqi.house.common.pojo.House;
import com.xunqi.house.common.pojo.User;
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
     * @param account
     * @return
     */
    public int insert(User account);

    /**
     * 查询小区信息
     * @param community
     * @return
     */
    List<Community> selectCommunity(Community community);
}
