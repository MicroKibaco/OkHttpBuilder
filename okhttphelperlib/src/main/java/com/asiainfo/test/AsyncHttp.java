package com.asiainfo.test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年03月31日19点58分 描述:okhttp 同步请求和异步请求
 */
public class AsyncHttp {

    public static void main(String[] args) {

        //  sendRequest("http://www.yangzhengyou.com");
        sendAsyncRequest("https://github.com/MicroKibaco");

    }


    /**
     * 描述:同步请求
     */
    private static void sendRequest(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 描述:异步请求
     */

    private static void sendAsyncRequest(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Asynchronous requests failed: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    System.out.println("An asynchronous request is successful: " + response.body().string());
                }

            }
        });

    }


}
