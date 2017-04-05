package com.asiainfo.okhttpbuilder.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.asiainfo.filedownload.http.DownloadCallBack;
import com.asiainfo.filedownload.manager.DownloadManager;
import com.asiainfo.filedownload.manager.FileStorageManager;
import com.asiainfo.filedownload.manager.HttpManager;
import com.asiainfo.filedownload.utils.Logger;
import com.asiainfo.okhttpbuilder.R;

import java.io.File;

public class MainActivity extends Activity {

    private static final String IMAGE_URL = "http://www.yangzhengyou.com/images/logo.png";
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView image = (ImageView) findViewById(R.id.image);

        File file = FileStorageManager.getInstance().getFileByName(IMAGE_URL);

        // HttpManagerTest(image);

        // DownloadManagerTest(image);

    }

    private void DownloadManagerTest(final ImageView image) {
        DownloadManager.getInstance().download(IMAGE_URL, new DownloadCallBack() {
            @Override
            public void sucess(File file) {

                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image.setImageBitmap(bitmap);
                    }
                });
                Logger.error("Microkibaco", "sucess " + file.getAbsolutePath());

            }

            @Override
            public void fail(int errorCode, String errorMsg) {

                Logger.error("Microkibaco", "fail " + errorCode + " " + errorMsg);

            }

            @Override
            public void Progress(int progress) {

            }
        });
    }

    private void HttpManagerTest(final ImageView image) {
        HttpManager.getInstance().asyncRequest(IMAGE_URL, new DownloadCallBack() {

            @Override
            public void sucess(File file) {

                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image.setImageBitmap(bitmap);
                    }
                });
                Logger.error("MicroKibaco", "sucess " + file.getAbsolutePath());

            }

            @Override
            public void fail(int errorCode, String errorMsg) {
                Logger.error("MicroKibaco", "fail " + errorCode + "  " + errorMsg);

            }

            @Override
            public void Progress(int progress) {


            }
        });
    }
}
