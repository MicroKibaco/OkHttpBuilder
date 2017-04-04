package com.asiainfo.filedownload.thread;

import com.asiainfo.filedownload.http.DownloadCallBack;
import com.asiainfo.filedownload.manager.FileStorageManager;
import com.asiainfo.filedownload.manager.HttpManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

/**
 * Created by MicroKibaco on 4/4/17.
 */

public class DownloadRunnable implements Runnable {

    private String mUrl;
    private long mStart;
    private long mEnd;
    private DownloadCallBack mCallBack;

    public DownloadRunnable(String url, long start, long end, DownloadCallBack callback) {
        mUrl = url;
        mStart = start;
        mEnd = end;
        mCallBack = callback;
    }

    @Override
    public void run() {

        Response response = HttpManager.getInstance().syncRequestByRange(mUrl, mStart, mEnd);

        if (response == null && mCallBack != null) {

            mCallBack.fail(HttpManager.NETWORK_CODE, "Network Request Failed!");
            return;
        }


        File file = FileStorageManager.getInstance().getFileByName(mUrl);

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");

            randomAccessFile.seek(mStart); // 指定偏移位置

            int len = 0;

            byte[] bytes = new byte[1024 * 500];

            InputStream inStream = response.body().byteStream();
            while ((len = inStream.read(bytes, 0, bytes.length)) != -1) {

                randomAccessFile.write(bytes, 0, len);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCallBack.sucess(file);

    }
}
