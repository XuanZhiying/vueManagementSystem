package com.xuanzy.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xuanzy.demo.entity.User;

import java.util.Date;

/**
 * Author: 轩志颍
 * Create: 2022-04-21 08:00
 **/
public class TokenUtils {

    /**
     * 过期时间5分钟
     **/
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
    * 生成Token
    * */
    public static String getToken(User user){
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token;
        token= JWT.create().withAudience(String.valueOf(user.getId())) // 将 user id 保存到 token 里面
                .withExpiresAt(date) //30分钟后token过期
                .sign(Algorithm.HMAC256(user.getPassword())); // 以 password 作为 token 的密钥
        return token;

    }
}
