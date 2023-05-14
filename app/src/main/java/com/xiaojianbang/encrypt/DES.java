package com.xiaojianbang.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import okio.ByteString;

public class DES {

    public static String encryptDES(String plainText) throws Exception {
        byte[] desKeyBytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        SecretKeySpec desKey = new SecretKeySpec(desKeyBytes, "DES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("12345678".getBytes());
        Cipher des = Cipher.getInstance("DES/CBC/PKCS5Padding");
        des.init(Cipher.ENCRYPT_MODE, desKey, ivParameterSpec);
        byte[] bytes = des.doFinal(plainText.getBytes());
        return ByteString.of(bytes).base64();
    }

    public static String decryptDES(String cipherText) throws Exception {
        byte[] desKeyBytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        SecretKeySpec desKey = new SecretKeySpec(desKeyBytes, "DES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("12345678".getBytes());
        Cipher des = Cipher.getInstance("DES/CBC/PKCS5Padding");
        des.init(Cipher.DECRYPT_MODE, desKey, ivParameterSpec);
        byte[] bytes = des.doFinal(ByteString.decodeBase64(cipherText).toByteArray());
        return new String(bytes);
    }

}
