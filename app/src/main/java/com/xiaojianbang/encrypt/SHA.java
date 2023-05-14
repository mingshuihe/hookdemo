package com.xiaojianbang.encrypt;

import java.security.MessageDigest;

import okio.ByteString;

public class SHA {

    public static String getSHA(String plainText) throws Exception {
        MessageDigest SHA = MessageDigest.getInstance("SHA-256");
        SHA.update((plainText + "saltstr").getBytes());
        byte[] digest = SHA.digest();
        return ByteString.of(digest).hex();
    }

}
