package com.xunqi.house.common.util;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * 密码加盐，防止明文的出现
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-31 11:32
 **/
public class HashUtils {

    private static final HashFunction FUNCTION = Hashing.md5();

    private static final String SALT = "xunqi.com";

    public static String encryPassword(String password) {
        HashCode hashCode = FUNCTION.hashString(password+SALT, Charset.forName("UTF-8"));
        return hashCode.toString();
    }


    public static void main(String[] args) {
        System.out.println(encryPassword("123456"));

    }
}
