package com.muggle.star.learn.spring.security.utils;

import java.security.*;

/**
 * RSA 工具类
 * @author lujianrong
 * @since 2020/12/3 19:33
 */
public class RsaUtils {


    private static final int DEFAULT_KEY_SIZE = 512;

    public static KeyPair getKey(String secret, int keySize) throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);

        return keyPairGenerator.genKeyPair();

    }

}
