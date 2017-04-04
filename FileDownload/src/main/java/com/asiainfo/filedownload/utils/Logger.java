package com.asiainfo.filedownload.utils;

import android.util.Log;

import java.util.Locale;

/**
 * Logger日志
 */

public class Logger {

    public static final boolean IS_DEBUG = true;

    public static void debug(String tag, String msg) {

        if (IS_DEBUG) {

            Log.d(tag, msg);
        }

    }

    public static void debug(String tag, String msg, Object... args) {

        if (IS_DEBUG) {

            Log.d(tag, String.format(Locale.getDefault(), msg, args));
        }

    }

    public static void error(String tag, String msg) {

        if (IS_DEBUG) {
            Log.e(tag, msg);

        }

    }

    public static void info(String tag, String msg) {

        if (IS_DEBUG) {

            Log.i(tag, msg);
        }

    }

}
