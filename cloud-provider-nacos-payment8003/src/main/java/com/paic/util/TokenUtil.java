package com.paic.util;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author zfyy04
 * @date 2022/5/5 9:46 PM
 */
@Component
public class TokenUtil {
    //过期时间，单位毫秒
    private long expireTime = 24 * 60 * 60 * 1000;
    //签名密钥，一般是放在数据库中
    private String tokenKey = "123456";

    public String getToken(String userName) {
        String token = Jwts.builder().setSubject(userName)
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, tokenKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    public String getUserNameByToken(String token) {
        String subject = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody().getSubject();
        return subject;
    }

}
