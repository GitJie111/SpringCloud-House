package com.xunqi.house.biz.service;

import com.xunqi.house.common.pojo.User;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-03 15:35
 **/
public interface AgentService {

    /**
     * 根据用户id查询经纪人结构
     * @param userId
     * @return
     */
    User getAgentDetail(Long userId);
}
