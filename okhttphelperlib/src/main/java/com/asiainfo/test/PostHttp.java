package com.asiainfo.test;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Post请求传递参数
 */

public class PostHttp {

    public static void main(String[] args) {

        FormBody body = new FormBody.Builder().add("username","MicroKibaco")
                .add("userage","99").build();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://localhost:8080/OkHttpBuilderWeb/HelloServlet").post(null).build();
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
