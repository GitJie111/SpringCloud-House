package com.xunqi.house.biz.mapper;

import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-03 15:52
 **/
@Mapper
public interface AgencyMapper {

    List<User> selectAgent(@Param("user")User user, @Param("pageParams")PageParams pageParams);

}
