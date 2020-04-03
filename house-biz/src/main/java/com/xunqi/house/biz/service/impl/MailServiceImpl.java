package com.xunqi.house.biz.service.impl;

import java.util.concurrent.TimeUnit;

import com.xunqi.house.biz.mapper.UserMapper;
import com.xunqi.house.biz.service.MailService;
import com.xunqi.house.common.pojo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import javax.annotation.Resource;


/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-31 14:10
 **/
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Resource
    private UserMapper userMapper;

    @Value("${domain.name}")
    private String domainName;

    @Value("${spring.mail.username}")
    private String from;

    private final Cache<String,String> registerCache = CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterAccess(15, TimeUnit.MINUTES).removalListener(
                    new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    userMapper.delete(notification.getValue());
                }
            }).build();

    @Override
    public void sendMail(String title, String url, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setText(url);
        mailSender.send(message);
    }


    /**
     * 1.缓存key-email的关系
     * 2.借助spring mail 发送邮件
     * 2.借助异步框架进行异步操作
     * @param email
     */
    @Async      //开启一个线程池，异步
    @Override
    public void registerNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey,email);
        String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
        sendMail("房产平台激活邮件",url,email);
    }

    @Override
    public boolean enable(String key) {
        String email = registerCache.getIfPresent(key);
        if (StringUtils.isBlank(email)) {
            return false;
        }
        User userUpdate = new User();
        userUpdate.setEmail(email);
        userUpdate.setEnable(1);
        userMapper.update(userUpdate);
        registerCache.invalidate(key);

        return true;
    }
}
