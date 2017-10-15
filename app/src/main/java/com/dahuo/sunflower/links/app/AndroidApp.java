package com.dahuo.sunflower.links;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.dahuo.sunflower.links.common.Constants;
import com.dahuo.sunflower.links.common.Logger;
import com.squareup.leakcanary.LeakCanary;

import java.lang.ref.WeakReference;

import io.fabric.sdk.android.Fabric;

/**
 * @author YanLu
 * @since 17/9/2
 */

public class AndroidApp extends Application {

    private static WeakReference<AndroidApp> sInstance;

    private static boolean sIsThemeChange = false;
    private static int sTheme = -1;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Logger.setEnabled(BuildConfig.isDebug);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = new WeakReference<>(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        // debug 模式不开启
        if(!Logger.isEnabled()) {
            Fabric.with(this, new Crashlytics());
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sTheme = Integer.parseInt(sp.getString(Constants.THEME_KEY, "0"));
    }

    public static AndroidApp getInstance() {
        return sInstance.get();
    }

    public static int getAppTheme() {
        return sTheme;
    }

    public static void setAppTheme(int theme) {
        sIsThemeChange |= (sTheme != theme);
        sTheme = theme;
    }

    public static boolean isThemeChange() {
        return sIsThemeChange;
    }

    public static void setThemeChange(boolean isThemeChange) {
        sIsThemeChange = isThemeChange;
    }

}
