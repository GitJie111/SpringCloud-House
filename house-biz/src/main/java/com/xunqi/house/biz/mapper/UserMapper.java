package com.xunqi.house.biz.mapper;

import com.xunqi.house.common.pojo.User;
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

    int insert(User account);

    int delete(String email);

    int update(User userUpdate);

    /**
     * 查询用户
     * @param user
     * @return
     */
    List<User> selectUserByQuery(User user);
}
