package com.asiainfo.filedownload.manager;

import android.support.annotation.NonNull;

import com.asiainfo.filedownload.db.DownloadEntity;
import com.asiainfo.filedownload.db.DownloadHelper;
import com.asiainfo.filedownload.http.DownloadCallBack;
import com.asiainfo.filedownload.thread.DownloadRunnable;
import com.asiainfo.filedownload.thread.DownloadTask;
import com.asiainfo.filedownload.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
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
            (MAX_THREAD, MAX_THREAD, 60, TimeUnit.DAYS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {


                private AtomicInteger mAtomicInteger = new AtomicInteger(1);


                @Override
                public Thread newThread(@NonNull Runnable r) {

                    Thread thread = new Thread(r, "download thread #" + mAtomicInteger.getAndIncrement());
                    return thread;
                }
            });
    private List<DownloadEntity> mCache;
    private DownloadEntity mEntity;
    private HashSet<DownloadTask> mDownloadTasks = new HashSet<>();

    private DownloadManager() {

    }

    public static DownloadManager getInstance() {
        return ourInstance;
    }


    public void download(final String url, final DownloadCallBack callBack) {

        final DownloadTask task = new DownloadTask(url, callBack);
        if (mDownloadTasks.contains(task)) {
            callBack.fail(HttpManager.TASK_RUNNING_ERROR_CODE, "Machine Translation");
            return;

        }

        mDownloadTasks.add(task);

        mCache = DownloadHelper.getInstance().getAll(url);

        if (mCache == null || mCache.size() == 0) {

            finish(task);
            Logger.debug("MicroKibaco", "onFailure ");


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

                    processDownload(url, length, callBack, mCache);
                    finish(task);

                }


            });

        } else {

            // TODO: 处理已经下载过的数据


        }
    }

    public void finish(DownloadTask task) {

        mDownloadTasks.remove(task);

    }

    private void processDownload(String url, long length, DownloadCallBack callback, List<DownloadEntity> cache) {

        long threadDownloadSize = length / MAX_THREAD;

        if (cache == null && cache.size() == 0) {

            mCache = new ArrayList<>();

        }

        for (int i = 0; i < MAX_THREAD; i++) {

            DownloadEntity downloadEntity = new DownloadEntity();

            long startSize = i * threadDownloadSize;

            long endSize = 0;

            endSize = endSize == MAX_THREAD - 1 ? length - 1 : (i + 1) * threadDownloadSize - 1;

            downloadEntity.setEnd_position(endSize);
            downloadEntity.setDownload_url(url);
            downloadEntity.setThread_id(i + 1);
            downloadEntity.setStart_position(startSize);
            mExecutor.execute(new DownloadRunnable(url, startSize, endSize, callback, downloadEntity));

        }


    }
}
