package com.muggle.star.learn.spring.security.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.*;
import java.util.Date;

/**
 * @author Madison
 * @since 2020/12/3 19:05
 */
@Component
public class JwtTokenHelper implements InitializingBean {

    public static final String ISSUER = "MuggleStar";

    @Value("${jwt.key.rsa.private}")
    private static String rsaPrivateKey;

    private static KeyPair keyPair;

    /**
     * 过期时间1小时
     */
    private static final Long ONE_HOUR = 3600000L;
    /**
     * 过期时间1个月
     */
    private static final Long ONE_MONTH = ONE_HOUR * 24 * 30;

    @Override
    public void afterPropertiesSet() throws Exception {
        keyPair = RsaUtils.createRSAKey(rsaPrivateKey, 1024);
    }


    /**
     * 创建token
     *
     * @param username
     * @param isRememberMe
     * @return
     */
    public static String createToken(String username, boolean isRememberMe) {

        // 有效时间
        long effectiveTime;
        if (isRememberMe) {
            effectiveTime = System.currentTimeMillis() + ONE_MONTH;
        } else {
            effectiveTime = System.currentTimeMillis() + ONE_HOUR;
        }

        return Jwts.builder()
                .signWith(SignatureAlgorithm.RS512, keyPair.getPrivate())
                .setIssuer(ISSUER)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(effectiveTime))
                .compact();
    }

    /**
     * 从token中获取用户名(此处的token是指去掉前缀之后的)
     *
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        String username;
        try {
            username = getTokenBody(token).getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }


    /**
     * 解析token
     *
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token) {
        try {
            return Jwts.parser().setSigningKey(keyPair.getPrivate()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException
                | UnsupportedJwtException
                | MalformedJwtException
                | SignatureException
                | IllegalArgumentException e) {
        }
        return null;
    }

    /**
     * 是否已过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


}
