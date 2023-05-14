package com.xiaojianbang.encrypt;

import android.util.Log;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;

import okio.ByteString;

public class Signature_ {

    public static String getSignature(String data) throws Exception {
        PrivateKey privateKey = RSA_Base64.generatePrivateKey();
        Log.d("xiaojianbang", "Signature privateKey: " + Arrays.toString(privateKey.getEncoded()));
        Signature sha256withRSA = Signature.getInstance("SHA256withRSA");
        sha256withRSA.initSign(privateKey);
        sha256withRSA.update(data.getBytes());
        byte[] sign = sha256withRSA.sign();
        return ByteString.of(sign).base64();
    }

    public static boolean verifySignature(String data, String sign) throws Exception {
        PublicKey publicKey = RSA_Base64.generatePublicKey();
        Log.d("xiaojianbang", "Signature publicKey: " + Arrays.toString(publicKey.getEncoded()));
        Signature sha256withRSA = Signature.getInstance("SHA256withRSA");
        sha256withRSA.initVerify(publicKey);
        sha256withRSA.update(data.getBytes());
        return sha256withRSA.verify(ByteString.decodeBase64(sign).toByteArray());
    }

}
