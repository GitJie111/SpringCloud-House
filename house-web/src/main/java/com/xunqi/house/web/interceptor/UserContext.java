package com.xunqi.house.web.interceptor;

import com.xunqi.house.common.pojo.User;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-01 09:12
 **/
public class UserContext {

    private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<User> ();

    public static void setUser(User user) {
        USER_HOLDER.set(user);
    }

    public static void remove() {
        USER_HOLDER.remove();
    }

    public static User getUser() {
        return USER_HOLDER.get();
    }

}
