package com.asiainfo.okhttpbuilder.app;

import android.app.Application;

import com.asiainfo.filedownload.db.DownloadHelper;
import com.asiainfo.filedownload.manager.FileStorageManager;
import com.asiainfo.filedownload.manager.HttpManager;

/**
 * Created by MicroKibaco on 4/4/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
        HttpManager.getInstance().init(this);
        DownloadHelper.getInstance().init(this);
    }


}
