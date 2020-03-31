package com.xunqi.house.util;

import com.xunqi.house.common.pojo.User;
import com.xunqi.house.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;


/**
 * 效验工具类
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-31 11:08
 **/
public class UserHelper {

    public static ResultMsg validate(User accout) {

        if (StringUtils.isBlank(accout.getEmail())) {
            return ResultMsg.errorMsg("Email 有误!");
        }
        if (StringUtils.isBlank(accout.getName())) {
            return ResultMsg.errorMsg("名字有误!");
        }

        if (StringUtils.isBlank(accout.getConfirmPasswd()) || StringUtils.isBlank(accout.getPasswd())
                || !accout.getPasswd().equals(accout.getConfirmPasswd())) {
            return ResultMsg.errorMsg("Email 有误");
        }

        if (accout.getPasswd().length() < 6) {
            return ResultMsg.errorMsg("密码大于6位");
        }

        return ResultMsg.successMsg("");
    }

}
