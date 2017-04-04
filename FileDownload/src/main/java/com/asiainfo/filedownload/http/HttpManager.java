package com.asiainfo.filedownload.http;

import android.content.Context;

import com.asiainfo.filedownload.file.FileStorageManager;
import com.asiainfo.filedownload.utils.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MicroKibaco on 4/4/17.
 */

public class HttpManager {

    private static final HttpManager ourInstance = new HttpManager();
    private static final int NETWORK_CODE = 1;
    private Context mContext;
    private OkHttpClient mClient;

    private HttpManager() {
        mClient = new OkHttpClient();
    }

    public static HttpManager getInstance() {
        return ourInstance;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    /**
     * 同步请求
     */
    public Response syncRequest(String url) {


        Request request = new Request.Builder().url(url).build();
        try {
            return mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 异步请求
     */

    public void asyncRequest(final String url, final DownloadCallBack callBack) {

        final Request request = new Request.Builder().url(url).build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Logger.error("MicroKibaco", "网络请求失败,请检查网络: " + e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful() && callBack != null) {

                    callBack.fail(NETWORK_CODE, "网络请求失败");

                }

                File file = FileStorageManager.getInstance().getFileByName(url);

                Logger.error("MicroKibaco", "file: " + file.getAbsolutePath());

                int len = 0;
                byte[] bytes = new byte[1024 * 500];
                FileOutputStream fileOut = new FileOutputStream(file);

                InputStream inStream = response.body().byteStream();
                while ((len = inStream.read(bytes, 0, bytes.length)) != -1) {

                    fileOut.write(bytes, 0, len);
                    fileOut.flush();

                }

                callBack.sucess(file);
            }
        });

    }
}
