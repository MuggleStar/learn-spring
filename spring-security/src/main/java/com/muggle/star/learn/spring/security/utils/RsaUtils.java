package com.muggle.star.learn.spring.security.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.*;

/**
 * RSA 工具类
 * @author MuggleStar
 * @since 2020/12/3 19:33
 */
@Component
public class RsaUtils {

    private static final int DEFAULT_KEY_SIZE = 1024;

    /**
     * 生成公钥与私钥
     *
     * @param secret
     * @param keySize
     * @return
     */
    public static KeyPair createRSAKey(String secret, int keySize) {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            if (secret != null) {
                // 固定生成
                SecureRandom secureRandom = new SecureRandom(secret.getBytes());
                keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
            } else {
                // 随机生成
                keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE));
            }

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
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) privateKey;

            // 获取公钥
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(rsaPrivateCrtKey.getModulus(), rsaPrivateCrtKey.getPublicExponent());
            PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);

            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
