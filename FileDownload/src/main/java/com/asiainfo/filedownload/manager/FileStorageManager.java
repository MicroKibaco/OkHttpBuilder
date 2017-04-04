package com.asiainfo.filedownload.manager;

import android.content.Context;
import android.os.Environment;

import com.asiainfo.filedownload.utils.Md5Utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by MicroKibaco on 4/4/17.
 */

public class FileStorageManager {
    private static final FileStorageManager ourInstance = new FileStorageManager();

    private Context mContext;

    private FileStorageManager() {
    }

    public static FileStorageManager getInstance() {
        return ourInstance;
    }

    public void init(Context context) {
        this.mContext = context;

    }

    public File getFileByName(String url) {

        File parent;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            parent = mContext.getExternalCacheDir();

        } else {

            parent = mContext.getCacheDir();
        }

        String fileName = Md5Utils.generateCode(url);

        File file = new File(parent, fileName);

        if (!file.exists()) {

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return file;

    }
}
