package com.xiaojianbang.encrypt;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import okio.ByteString;

public class RSA_Base64 {

    public static String publicKeyBase64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDxRQHxL/8xZ1EaNmQBGZnpMiCY" +
        "7gRzog6nDjfBJacytEiVJnJRuq1V/D+JKaXDwetsCnSUaz65LCFHU09OSEYee5oC" +
        "iI0ql21EA306c91oT/fQpPngQGZHLUtDOUdJVlAKnicCvmR24NqyNKFuY8L0cnB1" +
        "zcax73Rf+Ctf/lxAOwIDAQAB";

    public static String privateKeyBase64 = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAPFFAfEv/zFnURo2\n" +
            "ZAEZmekyIJjuBHOiDqcON8ElpzK0SJUmclG6rVX8P4kppcPB62wKdJRrPrksIUdT\n" +
            "T05IRh57mgKIjSqXbUQDfTpz3WhP99Ck+eBAZkctS0M5R0lWUAqeJwK+ZHbg2rI0\n" +
            "oW5jwvRycHXNxrHvdF/4K1/+XEA7AgMBAAECgYEAsGkDrYWps0bW7zKb1o4Qkojb\n" +
            "etZ2HNJ+ojlsHObaJOHbPGs7JXU4bmmdTz5LfSIacAoJCciMuTqCLrPEhfmkghPq\n" +
            "U2MjyjfqYdXALoP7l/vt6QmjY/g1IAsaZN9nFhyjJ2WzgOx1f7gZj4NBSvTdSj7H\n" +
            "m5E24zkm+p7Qw1z6/mkCQQD7WSXAXcv2v3Vo6qi1FUlkzQgCQLFYqXNSOSPpno3y\n" +
            "oohUFIkMj0bYGbVE1LzV30Rb6Z8e8yQAByw6l8RuGb2PAkEA9bwb2euyOe6CcqpE\n" +
            "PNFc+7UlOJAy5epVFKHbu0aNivVpU0hsphqjIGXJGHYTspyEOLqtzILqKPZr6pru\n" +
            "WvJUlQJBAJoImQUZtlyCGs7wN/G5mN/ocscGpGikd+Lk16hdHbqbdpaoexCyYYUf\n" +
            "xCHpicw75mW5d2V9Ngu6WZWS2rNqnOsCQCoMK//X8sEy7KNOOyrk8DIpxtqs4eix\n" +
            "dil3oK+k3OdgIsubYuvxNuR+RjCnU6uGWKGUX9TUudiUgda89/gb6xkCQFm8gD6n\n" +
            "AyN+PPPKRq2M84+cAbnvjdIAY3OFHfkaoWCtEj5DR0UDuVv7jN7+re2D7id/GkAe\n" +
            "FAmhvYQwwLnifrw=";

    public static PublicKey generatePublicKey() throws Exception {
        byte[] publicKeyBase64Bytes = ByteString.decodeBase64(publicKeyBase64).toByteArray();
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBase64Bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    public static PrivateKey generatePrivateKey() throws Exception {
        byte[] privateKeyBase64Bytes = ByteString.decodeBase64(privateKeyBase64).toByteArray();
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBase64Bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    public static String encryptByPublicKey(String plainText) throws Exception {
        PublicKey publicKey = generatePublicKey();
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = instance.doFinal(plainText.getBytes());
        return ByteString.of(bytes).base64();
    }

    public static String decryptByPrivateKey(String cipherText) throws Exception {
        PrivateKey privateKey = generatePrivateKey();
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = instance.doFinal(ByteString.decodeBase64(cipherText).toByteArray());
        return new String(bytes);
    }

}
