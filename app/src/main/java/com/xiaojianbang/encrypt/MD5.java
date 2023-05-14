package com.xiaojianbang.encrypt;

import java.security.MessageDigest;

import okio.ByteString;

public class MD5 {

    public static String getMD5(String plainText) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update((plainText + "saltstr").getBytes());
        byte[] digest = md5.digest();
        return ByteString.of(digest).hex();
    }

}
