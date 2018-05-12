package com.done.ijkplayer.utils;

import android.util.Log;

/**
 * Created by XDONE on 2017/12/19.
 */

public class LogUtils {
    private static boolean DEBUG = true;

    public LogUtils() {
    }

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static void v(String tag, String message) {
        if(DEBUG) {
            Log.v(tag, message);
        }

    }

    public static void d(String tag, String message) {
        if(DEBUG) {
            Log.d(tag, message);
        }

    }

    public static void i(String tag, String message) {
        if(DEBUG) {
            Log.i(tag, message);
        }

    }

    public static void w(String tag, String message) {
        if(DEBUG) {
            Log.w(tag, message);
        }

    }

    public static void e(String tag, String message) {
        if(DEBUG) {
            Log.e(tag, message);
        }

    }

    public static void e(String tag, String message, Exception e) {
        if(DEBUG) {
            Log.e(tag, message, e);
        }

    }
}
