package com.muggle.star.learn.spring.security.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lujianrong
 * @since 2020/12/3 19:05
 */
@Component
public class JwtTokenHelper implements InitializingBean {

    public static final String ISSUER = "Muggle Star";

    @Value("${jwt.key.rsa.private}")
    private String rsaPrivateKey;

    private Key jwtVerifierKey;

    /**
     * 过期时间1小时
     */
    private static final Long EXPIRATION = 3600L;

    private static final String ROLE = "role";

    @Override
    public void afterPropertiesSet() throws Exception {
        jwtVerifierKey = RsaUtils.createRSAKey("str", 1).getPrivate();
    }


    /**
     * 创建token
     *
     * @param username
     * @param role
     * @param isRememberMe
     * @return
     */
    public static String createToken(String username, String role, boolean isRememberMe) {
        Map<String,Object> map = new HashMap<>();
        map.put(ROLE, role);

        KeyPair keyPair = RsaUtils.createRSAKey("str", 1);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.RS512, keyPair.getPrivate())
                .setClaims(map)
                .setIssuer(ISSUER)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
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

    public static String getUserRole(String token) {
        return (String) getTokenBody(token).get(ROLE);
    }


    /**
     *
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token) {
        Claims claims = null;
        try {

            KeyPair keyPair = RsaUtils.createRSAKey("str", 1);
            claims = Jwts.parser().setSigningKey(keyPair.getPrivate()).parseClaimsJws(token).getBody();

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return claims;
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
