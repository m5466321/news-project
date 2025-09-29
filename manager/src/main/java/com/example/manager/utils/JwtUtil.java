package com.example.manager.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


import java.util.Date;
import java.util.Map;

public class JwtUtil {
    //自定义密钥
    private static final String KEY = "news";

    //生成token
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .sign(Algorithm.HMAC256(KEY));
    }
    //验证token
    public static Map<String, Object> parseToken(String token) {
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
        }
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
