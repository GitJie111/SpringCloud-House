package com.xunqi.house.biz.service.impl;

import com.xunqi.house.biz.mapper.AgencyMapper;
import com.xunqi.house.biz.service.AgentService;
import com.xunqi.house.common.page.PageParams;
import com.xunqi.house.common.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-03 15:36
 **/
@Service("agentService")
public class AgentServiceImpl implements AgentService {


    @Resource
    private AgencyMapper agencyMapper;

    @Value("${file.prefix}")
    private String imgPrefix;

    /**
     * 通过user表获取详情
     * 添加用户头像
     * @param userId
     * @return
     */
    @Override
    public User getAgentDetail(Long userId) {

        User user = User.builder()
                .id(userId).type(2).build();

        List<User> userList = agencyMapper.selectAgent(user, PageParams.build(1, 1));

        //接收用户头像
        setImg(userList);

        //如果查询出来的数据不为空则返回第一条数据,否则返回null
        if (!userList.isEmpty()) {
            return userList.get(0);
        }

        return null;
    }

    /**
     * 处理用户头像
     * @param userList
     */
    private void setImg(List<User> userList) {
        //利用foreach读取用户头像地址
        userList.forEach(i -> {
            i.setAvatar(imgPrefix + i.getAvatar());
        });
    }
}
