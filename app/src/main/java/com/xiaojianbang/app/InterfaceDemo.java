package com.xiaojianbang.app;

import android.util.Log;

public class InterfaceDemo implements TestRegisterClass {

    @Override
    public void test1() {
        Log.d("xiaojianbang", "test1() is called");
    }

    @Override
    public void test1(String a, int b) {
        Log.d("xiaojianbang", "test1(String a, int b) is called");
    }

    @Override
    public String test2(String a, int b) {
        return "test2(String a, int b) is called!";
    }
}
