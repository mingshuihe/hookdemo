package com.xiaojianbang.encrypt;

import android.util.Log;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

import okio.ByteString;

public class RSA_Hex {

    public static String modulus = "dfd5c4f8de188022477367ba9c3d8b367cfba7810f59702e1cd05bbd70179112c748b44" +
            "cfe5b79ebeb8732a8b4fd480acddaff6438fbf9e810cf7b0b57f1d60d1c257af668b22841099801086258d0" +
            "ec77c3f1774d6a28192d227793a788a64f7841f1703954155d81f115b56cf4aff3f91b5283d728b164b59a9" +
            "116d6f46ee7";
    public static String publicExponent = "010001";
    public static String privateExponent = "d4f09e198fd84915d35983c5f0db4fb3ff84bd1eb78683852ade41585fe9db9" +
            "6bcc57ad980657000b3a983d521918f56cfedd666f71b27ecd109416a5238fc42e71d4e276e505f5e38dada" +
            "acac257a444e9c74e3dd5b64fac6383ef11f1f3e09cc643959a1757941123b086b1ceb6aeceb8d2235c8b6f" +
            "840bdaf1da06ae51229";

    public static PublicKey generatePublicKey() throws Exception {
        BigInteger n = new BigInteger(modulus, 16);
        BigInteger e = new BigInteger(publicExponent, 16);
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(n, e);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(rsaPublicKeySpec);
    }

    public static PrivateKey generatePrivateKey() throws Exception {
        BigInteger n = new BigInteger(modulus, 16);
        BigInteger d = new BigInteger(privateExponent, 16);
        RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(n, d);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(rsaPrivateKeySpec);
    }

    public static String encryptByPublicKey(String plainText) throws Exception {
        PublicKey publicKey = generatePublicKey();
        Cipher instance = Cipher.getInstance("RSA/ECB/NOPadding");
        Log.d("xiaojianbang", "RSA_Hex publicKey: " + Arrays.toString(publicKey.getEncoded()));
        instance.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = instance.doFinal(plainText.getBytes());
        return ByteString.of(bytes).base64();
    }

    public static String decryptByPrivateKey(String cipherText) throws Exception {
        PrivateKey privateKey = generatePrivateKey();
        //Log.d("xiaojianbang", "RSA_Hex privateKey: " + Arrays.toString(privateKey.getEncoded()));
        Cipher instance = Cipher.getInstance("RSA/ECB/NOPadding");
        instance.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = instance.doFinal(ByteString.decodeBase64(cipherText).toByteArray());
        int index = -1;
        for (int i = bytes.length - 1; i > 0; i--) {
            if(bytes[i] == 0){
                index = i + 1;
                break;
            }
        }
        int newLength = bytes.length - index;
        byte[] newbytes = new byte[newLength];
        System.arraycopy(bytes, index, newbytes, 0, newLength);
        return new String(newbytes);
    }

}
