package com.asiainfo.filedownload.http;

import java.io.File;

/**
 * Created by MicroKibaco on 4/4/17.
 */

public interface DownloadCallBack {

    void sucess(File file);

    void fail(int errorCode, String errorMsg);

    void Progress(int progress);

}
