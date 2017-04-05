package com.asiainfo.filedownload.thread;

import com.asiainfo.filedownload.http.DownloadCallBack;

/**
 * Created by MicroKibaco on 4/5/17.
 */

public class DownloadTask {

    private String mUrl;
    private DownloadCallBack mDownloadCallBack;

    public DownloadTask(String url, DownloadCallBack downloadCallBack) {
        mUrl = url;
        mDownloadCallBack = downloadCallBack;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public DownloadCallBack getDownloadCallBack() {
        return mDownloadCallBack;
    }

    public void setDownloadCallBack(DownloadCallBack downloadCallBack) {
        mDownloadCallBack = downloadCallBack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadTask that = (DownloadTask) o;

        if (!mUrl.equals(that.mUrl)) return false;
        return mDownloadCallBack.equals(that.mDownloadCallBack);

    }

    @Override
    public int hashCode() {
        int result = mUrl.hashCode();
        result = 31 * result + mDownloadCallBack.hashCode();
        return result;
    }
}
