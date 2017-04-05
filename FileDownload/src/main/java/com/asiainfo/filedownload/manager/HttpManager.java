package com.asiainfo.filedownload.manager;

import android.content.Context;

import com.asiainfo.filedownload.http.DownloadCallBack;
import com.asiainfo.filedownload.utils.FileUtils;
import com.asiainfo.filedownload.utils.Logger;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HttpManager
 */

public class HttpManager {

    public static final int NETWORK_CODE = 1;
    public static final int NETWORK_ERROR_CODE = 2;
    public static final int TASK_RUNNING_ERROR_CODE = 3;
    private static final HttpManager ourInstance = new HttpManager();
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
     * Synchronous Invocation
     */
    public Response syncRequestByRange(String url, long start, long end) {

        Request request = new Request.Builder().url(url).
                addHeader("Range", "bytes=" + start + "-" + end).
                build();
        try {
            return mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Asynchronous Invocation
     */
    public void asyncRequest(String url, final Callback callback) {

        Request request = new Request.Builder().url(url).build();

        mClient.newCall(request).enqueue(callback);

    }

    /**
     * Asynchronous Invocation
     */

    public void asyncRequest(final String url, final DownloadCallBack callBack) {

        final Request request = new Request.Builder().url(url).build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Logger.error("MicroKibaco", "Network request failed, please check your network: " + e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful() && callBack != null) {

                    callBack.fail(NETWORK_CODE, "Network Request Failed!");

                }

                File file = FileStorageManager.getInstance().getFileByName(url);

                Logger.error("MicroKibaco", "file: " + file.getAbsolutePath());

                FileUtils.readFileBytes(response, file);

                callBack.sucess(file);
            }
        });

    }
}
