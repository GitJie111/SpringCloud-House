package com.xunqi.house.biz.service.impl;
import	java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.xunqi.house.biz.mapper.UserMapper;
import com.xunqi.house.biz.service.FileService;
import com.xunqi.house.biz.service.MailService;
import com.xunqi.house.biz.service.UserService;
import com.xunqi.house.common.pojo.User;
import com.xunqi.house.common.util.BeanHelper;
import com.xunqi.house.common.util.HashUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-25 09:31
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private FileService fileService;

    @Resource
    private MailService mailService;


    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
        List<String> imgList = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
        if (!imgList.isEmpty()) {
            account.setAvatar(imgList.get(0));
        }
        BeanHelper.setDefaultProp(account, User.class);
        BeanHelper.onInsert(account);
        account.setEnable(0);
        userMapper.insert(account);
        mailService.registerNotify(account.getEmail());
        return true;
    }

    @Override
    public boolean enable(String key) {

        return mailService.enable(key);
    }


}
