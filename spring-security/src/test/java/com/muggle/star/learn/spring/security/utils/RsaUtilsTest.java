package com.muggle.star.learn.spring.security.utils;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

/**
 * RSA 测试
 *
 * @author lujianrong
 * @since 2020/12/3
 */
public class RsaUtilsTest {

    @Test
    public void test(){
        try {
            KeyPair key = RsaUtils.getKey("Life is endless, struggle is endless!", 10);
            BASE64Encoder base64Encoder = new BASE64Encoder();
            System.out.println("=========================");
            System.out.println(base64Encoder.encode(key.getPublic().getEncoded()));
            System.out.println("=========================");
            System.out.println(base64Encoder.encode(key.getPrivate().getEncoded()));
            System.out.println("=========================");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}