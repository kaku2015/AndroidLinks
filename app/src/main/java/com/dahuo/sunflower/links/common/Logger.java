package com.dahuo.sunflower.links.common;

import android.util.Log;

/**
 * @author YanLu
 * @since 17/3/22
 */

public class Logger {
    private static boolean sEnabled = false;

    public static boolean isEnabled() {
        return sEnabled;
    }
//
    public static void setEnabled(boolean enabled) {
        sEnabled = enabled;
    }

    public static void d(String msg) {
        if (sEnabled) {
            d("Logger", msg);
        }
    }

    public static void d(String tag, String msg) {
        if (sEnabled) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable t) {
        if (sEnabled) {
            Log.d(tag, msg, t);
        }
    }

    public static void d(String msg, Throwable t) {
        if (sEnabled) {
            Log.d("", msg, t);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (sEnabled) {
            Log.e(tag, msg, t);
        }
    }

    public static void e(String tag, String msg) {
        if (sEnabled) {
            Log.e(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (sEnabled) {
            Log.i(tag, msg);
        }
    }
}
