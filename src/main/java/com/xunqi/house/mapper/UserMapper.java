package com.xunqi.house.mapper;

import com.xunqi.house.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-25 09:05
 **/
@Mapper
public interface UserMapper {

    /**
     * 查询全部用户
     * @return
     */
    List<User> findAll();

}
