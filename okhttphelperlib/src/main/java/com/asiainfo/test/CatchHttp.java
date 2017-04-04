package com.asiainfo.test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * oKHttp 实现缓存机制
 */

public class CatchHttp {

    public static void main(String[] args) throws IOException {

        int maxCacheSize = 10 * 1024 * 1024;

        Cache cache = new Cache(new File("/Users/MicroKibaco/Desktop/Android/cache"), maxCacheSize);

        OkHttpClient client = new OkHttpClient().newBuilder().cache(cache).build();

        Request request = new Request.Builder().url("http://www.yangzhengyou.com")
                .cacheControl(new CacheControl.Builder().maxStale(365, TimeUnit.DAYS).build())
                .build();


        Response response = client.newCall(request).execute();

        // 读取网络缓存的内容
        String body = response.body().string();

        if (response.isSuccessful()) {

            System.out.println("net work response: " + response.networkResponse());
            System.out.println("cache response: " + response.cacheResponse());
            System.out.println("------------------------------------------------");


        }
        Response response1 = client.newCall(request).execute();
        String body1 = response1.body().string();

        if (response1.isSuccessful()) {

            System.out.println("net work response1: " + response1.networkResponse());
            System.out.println("cache response1: " + response1.cacheResponse());
            System.out.println("------------------------------------------------");


        }

    }
}
