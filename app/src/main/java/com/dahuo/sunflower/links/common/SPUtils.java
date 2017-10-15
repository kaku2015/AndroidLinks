package com.dahuo.sunflower.links.common;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Set;

/**
 */
public final class SPUtils {

    /**
     * get a pref with given name
     *
     * @param context  Context
     * @return
     */
    public static SharedPreferences getDefault(Context context) {
        return context.getApplicationContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences get(Context context, String prefName) {
        return context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }


    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sp = getDefault(context);
        if (sp != null) {
            return sp.getString(key, defaultValue);
        }
        return defaultValue;
    }



    /**
     * save value to pref
     *
     * @param context  Context
     * @param value
     */
    @TargetApi(11)
    public static void save(Context context, String key, Object value) {
        SharedPreferences sp = getDefault(context);
        if (sp != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = sp.edit();
            if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Set<?>) {
                editor.putStringSet(key, (Set<String>) value);
            } else {
                if (value != null) {
                    editor.putString(key, value.toString());
                } else {
                    editor.putString(key, "");
                }
            }
            editor.apply();
        }
    }


    public static void remove(Context context, String key) {
        SharedPreferences sp = getDefault(context);
        if (sp != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.apply();
        }
    }
}
