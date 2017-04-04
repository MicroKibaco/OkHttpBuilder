package com.asiainfo.filedownload.http;

import java.io.File;

/**
 * 多线程下载回调
 */

public interface DownloadCallBack {

    void sucess(File file);

    void fail(int errorCode, String errorMsg);

    void Progress(int progress);

}
