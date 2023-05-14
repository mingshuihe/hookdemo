package com.xiaojianbang.ndk;

public class NativeHelper {

    static {
        System.loadLibrary("xiaojianbang");
    }

    public native static int add(int a, int b, int c);
    public native static String encode();
    public native static String md5(String str);
    public native static void readSomething();

}
