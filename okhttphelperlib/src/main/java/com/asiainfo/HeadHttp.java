package com.asiainfo;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年03月31日21点13分 描述:
 */
public class HeadHttp {

    public static void main(String[] args) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url("http://www.yangzhengyou.com").
                addHeader("User-Agent", "from nate http").
                addHeader("Accept","text/plain, text/html").
                build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {

                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {

                    System.out.println(headers.name(i) + ":" + headers.value(i));

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
