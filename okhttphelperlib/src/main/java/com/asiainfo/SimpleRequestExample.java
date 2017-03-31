package com.asiainfo;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年03月31日15点49分 描述:Okhttp3.0请求简易示例
 */
public class SimpleRequestExample {

    public static void main(String args[]) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.yangzhengyou.com").build(); // 构建者设计模式

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
