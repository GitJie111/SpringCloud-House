package com.xunqi.house.common.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-03 16:06
 **/
@Data
public class UserMsg {

    private Long id;

    private String msg;

    private Long userId;

    private Date createTime;

    /**
     * 经纪人id
     */
    private Long agentId;

    private Long houseId;

    private String email;

    private String userName;


}
