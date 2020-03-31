package com.xunqi.house.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

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
    private Long id;

    private String email;

    private String phone;

    private String name;

    private String passwd;

    private String confirmPasswd;

    private Integer type;//普通用户1，经纪人2

    private Date   createTime;

    private Integer enable;

    private String  avatar;

    private MultipartFile avatarFile;

    private String newPassword;

    private String key;

    private Long   agencyId;

    private String aboutme;

    private String agencyName;

}