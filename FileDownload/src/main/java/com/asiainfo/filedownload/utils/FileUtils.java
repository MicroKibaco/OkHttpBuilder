package com.asiainfo.filedownload.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by MicroKibaco on 4/4/17.
 */

public class FileUtils {

    public static File readFileBytes(Response response, File file) {

        int len = 0;
        try {
            byte[] bytes = new byte[1024 * 500];
            FileOutputStream fileOut = new FileOutputStream(file);

            InputStream inStream = response.body().byteStream();
            while ((len = inStream.read(bytes, 0, bytes.length)) != -1) {

                fileOut.write(bytes, 0, len);
                fileOut.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;

    }

}
