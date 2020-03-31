package com.xunqi.house.web.controller;

import com.xunqi.house.biz.service.UserService;
import com.xunqi.house.common.constants.CommonConstants;
import com.xunqi.house.common.pojo.User;
import com.xunqi.house.common.result.ResultMsg;
import com.xunqi.house.util.UserHelper;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-25 09:32
 **/
@Controller
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/")
    public String login() {
        return "user/accounts/signin";
    }

    /**
     * 注册提交
     * 1.注册验证
     * 2.发送邮件
     * 3.验证失败重定向到注册页面
     * 注册页获取：根据account对象为依据判断是否向注册页获取请求
     * @param account
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/accounts/register")
    public String accountRegister(User account, ModelMap modelMap) {

        if (account == null || account.getName() == null) {
            return "user/accounts/register";
        }

        //用户验证
        ResultMsg resultMsg = UserHelper.validate(account);
        if (resultMsg.isSuccess() && userService.addAccount(account)) {
            modelMap.put("email",account.getEmail());
            return "user/accounts/registerSubmit";
        } else {
            return "redirect:/accounts/register?" + resultMsg.asUrlParams();
        }
    }


    @RequestMapping(value = "/accounts/verify")
    public String verify(String key) {
        boolean result = userService.enable(key);
        if (result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:/account/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期").asUrlParams();
        }
    }



    //--------------------登录流程----------------------

    /**
     * 登录接口
     */
    @RequestMapping(value = "/accounts/signin")
    public String signin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String target = request.getParameter("target");

        if (username == null || password == null) {
            request.setAttribute("target",target);
            return "user/accounts/signin";
        }
        User user = userService.auth(username,password);

        if (user == null) {
            return "redirect:/account/signin?target=" + target + "&username=" +
                    username + "&" + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        } else {
            request.getSession().setAttribute(CommonConstants.USER_ATTRIBUTE,user);
            request.getSession().setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE,user);
            return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
        }
    }


    /**
     * 登出操作
     * @param request
     * @return
     */
    @RequestMapping(value = "/accounts/logout")
    public String logout(HttpServletRequest request) {
        //清除session
        request.getSession().invalidate();
        return "redirect:/index";
    }



}
