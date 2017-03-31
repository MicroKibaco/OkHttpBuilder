package com.asiainfo;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年03月31日21点32分 描述:http get请求之添加参数
 */
public class QueryHttp {

    public static void main(String[] args) {

        OkHttpClient client = new OkHttpClient();
        HttpUrl httpUrl = HttpUrl.parse("https://free-api.heweather.com/v5/weather").
                newBuilder().
                addQueryParameter("city", "beijing").
                addQueryParameter("key", "fddf5365e9fb475b801726468f979a18").
                build();
        String httpUrlStr = httpUrl.toString();
        Request request = new Request.Builder().url(httpUrlStr).build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
