package com.xiaojianbang.test;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Okhttp3Utils {

    public static OkHttpClient client = new OkHttpClient.Builder().build();

    public static void doRequest(){
        new Thread(){
            public void run(){
                FormBody formBody = new FormBody.Builder().add("user", "xiaojianbang").add("pass", "xiaojianbang").build();
                Request request = new Request.Builder()
                        .url("https://www.baidu.com/")
                        .post(formBody)
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

class LoggingInterceptor implements Interceptor {
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d("xiaojianbang", String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d("xiaojianbang", String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}





