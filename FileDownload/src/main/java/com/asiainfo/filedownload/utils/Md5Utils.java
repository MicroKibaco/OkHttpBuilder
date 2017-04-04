package com.asiainfo.filedownload.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5加密算法
 */

public class Md5Utils {

    public static String generateCode(String url) {

        if (TextUtils.isEmpty(url)) {

            return null;

        }
        StringBuffer buffer = new StringBuffer();

        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(url.getBytes());
            byte[] clipher = digest.digest();

            for (int i = 0; i < clipher.length; i++) {
                String hexStr = Integer.toHexString(i & 0xff);

                buffer.append(hexStr.length() == 1 ? "0" + hexStr : hexStr);

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return buffer.toString();

    }

}
