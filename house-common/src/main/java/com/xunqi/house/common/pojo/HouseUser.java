package com.xunqi.house.common.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-03 16:33
 **/
@Data
public class HouseUser {

    private Long id;
    private Long houseId;
    private Long userId;
    private Date createTime;
    private Integer type;

}
