package com.xunqi.house.biz.service;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-31 14:10
 **/
public interface MailService {

    /**
     * 发送邮件
     * @param title
     * @param url
     * @param email
     */
    void sendMail(String title, String url, String email);

    /**
     * 1.缓存key-email的关系
     * 2.借助spring mail 发送邮件
     * 3.借助异步框架进行异步操作
     * @param email
     */
    void registerNotify(String email);

    /**
     * 激活用户状态
     * @param key
     * @return
     */
    boolean enable(String key);
}
