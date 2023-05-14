package com.xiaojianbang.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import okio.ByteString;

public class AES {

    public static String encryptAES(String plainText,  String AESKey) throws Exception {
        SecretKeySpec aesKey = new SecretKeySpec(ByteString.decodeHex(AESKey).toByteArray(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("0123456789abcdef".getBytes());
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);
        byte[] bytes = aes.doFinal(plainText.getBytes());
        return ByteString.of(bytes).base64();
    }

    public static String decryptAES(String cipherText,  String AESKey) throws Exception {
        SecretKeySpec aesKey = new SecretKeySpec(ByteString.decodeHex(AESKey).toByteArray(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("0123456789abcdef".getBytes());
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
        byte[] bytes = aes.doFinal(ByteString.decodeBase64(cipherText).toByteArray());
        return new String(bytes);
    }

}
