package com.asiainfo;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okHttp使用mutipart协议上传文件
 * 1.修改域名url 2.在desktop手动创建 girl.jpg 3.测试
 */

public class MultipartHttp {

    public static void main(String[] args) {

        RequestBody imageBody = RequestBody.create(MediaType.parse("image/jpeg"),
                new File("/Users/MicroKibaco/girl.jpg"));

        MultipartBody body = new MultipartBody.Builder().
                setType(MultipartBody.FORM).
                addFormDataPart("filename", "girl.jpg", imageBody).
                addFormDataPart("name","MicroKibaco").
                build();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().
                url("http://15.15.20.254:8080/OkHttpBuilderWeb/UploadServlet").
                post(body).build();

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
