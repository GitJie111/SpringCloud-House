package com.xunqi.house.common.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * (Community)实体类
 *
 * @author makejava
 * @since 2020-04-02 09:33:28
 */
@Data
public class Community implements Serializable {

    private static final long serialVersionUID = -98421658916960050L;
    
    private Integer id;
    /**
    * 城市编码
    */
    private String cityCode;
    /**
    * 小区名称
    */
    private String name;
    /**
    * 城市名称
    */
    private String cityName;

}