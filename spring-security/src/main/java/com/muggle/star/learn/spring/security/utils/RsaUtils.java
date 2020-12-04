package com.muggle.star.learn.spring.security.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.*;

/**
 * RSA 工具类
 * @author lujianrong
 * @since 2020/12/3 19:33
 */
@Component
public class RsaUtils {

    private static final int DEFAULT_KEY_SIZE = 512;

    /**
     * 生成公钥与私钥
     *
     * @param secret
     * @param keySize
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair createRSAKey(String secret, int keySize) {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = new SecureRandom(secret.getBytes());
            keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);

            return keyPairGenerator.genKeyPair();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据私钥获取 KeyPair 对象
     *
     * @param privateKeyStr
     * @return
     */
    public static KeyPair getRSAKey(String privateKeyStr) {

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            // 解析私钥
            byte[] privateKeyBytes = Base64.decodeBase64(privateKeyStr);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            // 获取公钥
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(privateKey.getModulus(), privateKey.getPublicExponent());
            PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);

            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
