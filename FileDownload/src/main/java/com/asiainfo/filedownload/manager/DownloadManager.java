package com.asiainfo.filedownload.manager;

import android.support.annotation.NonNull;

import com.asiainfo.filedownload.http.DownloadCallBack;
import com.asiainfo.filedownload.thread.DownloadRunnable;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 下载管理器
 */

public class DownloadManager {

    private static final DownloadManager ourInstance = new DownloadManager();

    private static final int MAX_THREAD = 2;

    private static final ThreadPoolExecutor mExecutor = new ThreadPoolExecutor
            (MAX_THREAD, MAX_THREAD, 60, TimeUnit.DAYS, new SynchronousQueue<Runnable>(), new ThreadFactory() {


                private AtomicInteger mAtomicInteger = new AtomicInteger(1);


                @Override
                public Thread newThread(@NonNull Runnable r) {

                    Thread thread = new Thread(r, "download thread #" + mAtomicInteger.getAndIncrement());
                    return thread;
                }
            });

    private DownloadManager() {

    }

    public static DownloadManager getInstance() {
        return ourInstance;
    }

    public void download(final String url, final DownloadCallBack callBack) {

        HttpManager.getInstance().asyncRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful() && callBack != null) {

                    callBack.fail(HttpManager.NETWORK_CODE, "Network Request Failed!");
                    return;

                }

                long length = response.body().contentLength();

                if (length == -1) {
                    callBack.fail(HttpManager.NETWORK_ERROR_CODE, "content length is -1");
                }

                processDownload(url, length, callBack);

            }


        });


    }

    private void processDownload(String url, long length, DownloadCallBack callback) {

        long threadDownloadSize = length / MAX_THREAD;

        for (int i = 0; i < MAX_THREAD; i++) {

            long startSize = i * threadDownloadSize;
            long endSize = (i + 1) * threadDownloadSize - 1;
            mExecutor.execute(new DownloadRunnable(url, startSize, endSize, callback));

        }


    }
}