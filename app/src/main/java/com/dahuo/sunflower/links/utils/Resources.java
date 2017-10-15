package com.dahuo.sunflower.links.utils;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.util.TypedValue;

import com.dahuo.sunflower.links.R;


/**
 * @author YanLu
 * @since 17/9/9
 */
public final class Resources {

    public static int resolveResourceId(Context context, @AttrRes int resId) {
        final TypedValue value = new TypedValue();
        if (!context.getTheme().resolveAttribute(resId, value, true)) {
            return 0;
        }
        return value.resourceId;
    }

    public static int getAttrColor(Context context, @AttrRes int resId) {
        final TypedValue value = new TypedValue();
        return context.getTheme().resolveAttribute(resId, value, true)
                ? value.data : R.color.secondary_text_disabled_dark;
    }

    public static int getColor(Context context, @ColorRes int resId) {
        return context.getResources().getColor(resId);
    }
}
