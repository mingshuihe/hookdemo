package com.xiaojianbang.okhttp3;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataPost {

    public static void post(){
        new Thread(){
            public void run(){
                OkHttpClient client = MySSLSocketFactory.createClient();
                Request request = new Request.Builder()
                        .url("https://www.baidu.com/")
                        .get()
                        .addHeader(
                                "User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4098.3 Safari/537.36"
                        )
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Log.d("xiaojianbang", "response: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
