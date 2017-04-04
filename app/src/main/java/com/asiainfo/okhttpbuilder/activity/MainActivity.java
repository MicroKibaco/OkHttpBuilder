package com.asiainfo.okhttpbuilder.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.asiainfo.filedownload.file.FileStorageManager;
import com.asiainfo.filedownload.http.DownloadCallBack;
import com.asiainfo.filedownload.http.HttpManager;
import com.asiainfo.filedownload.utils.Logger;
import com.asiainfo.okhttpbuilder.R;

import java.io.File;

public class MainActivity extends Activity {

    private static final String IMAGE_URL = "https://img1.gtimg.com/ninja/2/2017/04/ninja149127598426898.jpg";
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView image = (ImageView) findViewById(R.id.image);

        File file = FileStorageManager.getInstance().getFileByName(IMAGE_URL);
        Logger.error("MicroKibaco", "file path: " + file.getAbsolutePath());

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