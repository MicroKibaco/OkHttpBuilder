package com.asiainfo.download;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http 字段content-length、range、Transfer-Encoding chunked实践
 */

public class RangeHttp {

    public static void main(String[] args) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url("https://imgcache.qq.com/ptlogin/v4/style/40/images/icon_3_tiny.png").
                // addHeader("Accept-Econding", "identity").
                        addHeader("Range", "bytes=0-").
                        build();

        try {
            Response response = client.newCall(request).execute();

            System.out.println("Content Length: " + response.body().contentLength());

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
