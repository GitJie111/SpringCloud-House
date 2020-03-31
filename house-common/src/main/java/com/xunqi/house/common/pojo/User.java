package com.xunqi.house.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-03-25 09:04:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = -79109494496006938L;
    /**
    * 主键
    */
    private Object id;
    /**
    * 姓名
    */
    private String name;
    /**
    * 手机号
    */
    private String phone;
    /**
    * 电子邮件
    */
    private String email;
    /**
    * 自我介绍
    */
    private String aboutme;
    /**
    * 经过MD5加密的密码
    */
    private String passwd;
    /**
    * 头像图片
    */
    private String avatar;
    /**
    * 1:普通用户，2:房产经纪人
    */
    private Object type;
    /**
    * 创建时间
    */
    private Object createTime;
    /**
    * 是否启用,1启用，0停用
    */
    private Object enable;
    /**
    * 所属经纪机构
    */
    private Integer agencyId;

}