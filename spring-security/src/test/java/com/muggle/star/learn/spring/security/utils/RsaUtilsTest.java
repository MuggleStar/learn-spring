package com.muggle.star.learn.spring.security.utils;

import com.muggle.star.learn.spring.security.SecurityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.security.KeyPair;

/**
 * RSA 测试
 *
 * @author lujianrong
 * @since 2020/12/3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SecurityApplication.class)
public class RsaUtilsTest {

    @Value("${jwt.key.rsa.private}")
    private String rsaPrivateKey;
    @Value("${jwt.key.rsa.public}")
    private String rsaPublicKey;

    @Resource
    private RsaUtils rsaUtils;


    @Test
    public void testCreateRSAKey() {

        KeyPair key = RsaUtils.createRSAKey("Life is endless, struggle is endless!", 10);
        BASE64Encoder base64Encoder = new BASE64Encoder();
        System.out.println("=========================");
        System.out.println(base64Encoder.encode(key.getPublic().getEncoded()));
        System.out.println("=========================");
        System.out.println(base64Encoder.encode(key.getPrivate().getEncoded()));
        System.out.println("=========================");

    }

    @Test
    public void testGetRSAKey() {

        KeyPair keyPair = rsaUtils.getRSAKey(rsaPrivateKey);
        BASE64Encoder base64Encoder = new BASE64Encoder();
        System.out.println("=========================");
        System.out.println(base64Encoder.encode(keyPair.getPublic().getEncoded()));
        System.out.println("=========================");
        System.out.println(base64Encoder.encode(keyPair.getPrivate().getEncoded()));
        System.out.println("=========================");

    }

}