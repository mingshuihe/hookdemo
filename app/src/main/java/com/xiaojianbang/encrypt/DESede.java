package com.xiaojianbang.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import okio.ByteString;

public class DESede {

    public static String encryptDESede(String plainText) throws Exception {
        byte[] desedeKeyBytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8,1, 2, 3, 4, 5, 6, 7, 8,1, 2, 3, 4, 5, 6, 7, 8};
        SecretKeySpec desedeKey = new SecretKeySpec(desedeKeyBytes, "DESede");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("12345678".getBytes());
        Cipher desede = Cipher.getInstance("DESede/CBC/NOPadding");
        desede.init(Cipher.ENCRYPT_MODE, desedeKey, ivParameterSpec);
        byte[] bytes = desede.doFinal(plainText.getBytes());
        return ByteString.of(bytes).base64();
    }

    public static String decryptDESede(String cipherText) throws Exception {
        byte[] desedeKeyBytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8,1, 2, 3, 4, 5, 6, 7, 8,1, 2, 3, 4, 5, 6, 7, 8};
        SecretKeySpec desedeKey = new SecretKeySpec(desedeKeyBytes, "DESede");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("12345678".getBytes());
        Cipher des = Cipher.getInstance("DESede/CBC/NOPadding");
        des.init(Cipher.DECRYPT_MODE, desedeKey, ivParameterSpec);
        byte[] bytes = des.doFinal(ByteString.decodeBase64(cipherText).toByteArray());
        return new String(bytes);
    }

}
