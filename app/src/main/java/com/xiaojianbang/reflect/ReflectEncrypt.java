package com.xiaojianbang.reflect;

import android.util.Base64;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.HashMap;

public class ReflectEncrypt {

    public static void onCreate() {

        try {
            String password = "12345678";

//          MessageDigest md5 = MessageDigest.getInstance("MD5");
            Class<?> a = Class.forName(dd("amF2YS5zZWN1cml0eS5NZXNzYWdlRGlnZXN0"));
            Method getInstance = a.getMethod(dd("Z2V0SW5zdGFuY2U="), String.class);
            Object b = getInstance.invoke(a, dd("TUQ1"));

//          md5.update(password.getBytes());
            Method update = a.getMethod(dd("dXBkYXRl"), byte[].class);
            update.invoke(b, password.getBytes());

//          byte[] digest = md5.digest();
            Method c = a.getMethod(dd("ZGlnZXN0"));
            byte[] d = (byte[])c.invoke(b);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(dd("cGFzc3dvcmQ="), Base64.encodeToString(d, 0));
            Log.d("xiaojianbang", hashMap.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String dd(String cipherText){
        byte[] decode = Base64.decode(cipherText, 0);
        return new String(decode);
    }

}
