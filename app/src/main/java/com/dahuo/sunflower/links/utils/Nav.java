package com.dahuo.sunflower.links.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.dahuo.sunflower.links.R;


/**
 * @author YanLu
 * @since 17/6/5
 */

public class Nav {

    public static void act(Activity context, Class<?> cls, int requestCode) {
        context.startActivityForResult(new Intent(context, cls), requestCode);
    }
    public static void act(Activity context, Class<?> cls) {
        openIntent(context, new Intent(context, cls));
    }
    public static void act(Activity context, Class<?> cls, Bundle args) {
        openIntent(context, new Intent(context, cls).putExtras(args));
    }


    public static void openIntent(Context context, Intent intent) {
        try {
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, R.string.failure, Toast.LENGTH_SHORT).show();
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, R.string.failure, Toast.LENGTH_SHORT).show();
        }
    }
    public static void openWithBrowser(Context context, String url) {
        //在浏览器打开
        try {
            context.startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(url)),
                    context.getString(R.string.chooser_browser)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, R.string.failure, Toast.LENGTH_SHORT).show();
        }
    }


    public static void openMarket(Context context, String targetPackage, String downloadUrl) {
        try {
            Intent googlePlayIntent = context.getPackageManager().getLaunchIntentForPackage("com.android.vending");
            ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity");
            // noinspection ConstantConditions
            googlePlayIntent.setComponent(comp);
            googlePlayIntent.setData(Uri.parse("market://details?id=" + targetPackage));
            context.startActivity(googlePlayIntent);
        } catch (Throwable e) {
            openIntent(context, new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl)));
            e.printStackTrace();
        }

    }
}
