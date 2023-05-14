package com.xiaojianbang.encrypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okio.ByteString;

public class MAC {

    public static String getMAC(String plainText) throws Exception {
        SecretKeySpec hmacMD5Key = new SecretKeySpec("a123456789".getBytes(), 1, 8, "HmacSHA1");
        Mac hmacMD5 = Mac.getInstance("HmacSHA1");
        hmacMD5.init(hmacMD5Key);
        hmacMD5.update(plainText.getBytes());
        byte[] bytes = hmacMD5.doFinal("saltstr".getBytes());
        return ByteString.of(bytes).hex();
    }

}
