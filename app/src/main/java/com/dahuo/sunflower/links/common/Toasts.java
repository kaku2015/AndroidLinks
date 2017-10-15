package com.dahuo.sunflower.links.common;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

import com.dahuo.sunflower.links.AndroidApp;


/**
 * @author YanLu
 * @since 17/9/16
 */

public class Toasts {

    public static void show(String msg) {
        try {
            Context context = AndroidApp.getInstance();
            if (context != null) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // nop
        }
    }

    public static void show(@StringRes int resId) {
        try {
            Context context = AndroidApp.getInstance();
            if (context != null) {
                Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // nop
        }
    }

    public static void show(@StringRes int resId, Rect bounds) {
        try {
            Context context = AndroidApp.getInstance();
            if (context != null) {
                Toast toast = Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.START, bounds.centerX(), bounds.centerY());
                toast.show();
            }

        } catch (Exception e) {
            // nop
        }
    }
}
