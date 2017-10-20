package com.dahuo.sunflower.links.utils;

import android.content.Context;
import android.text.TextUtils;

import com.dahuo.sunflower.links.common.Logger;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author YanLu
 * @since 17/9/25
 */
public class AssetUtils {

    private static final String TAG = "AssetUtil";

    /**
     * get data from assert
     *
     * @param fileName
     * @return class you want to return
     */
    public static String getAssetContent(Context context, String fileName) {
        InputStream fin = null;
        try {
            fin = context.getResources().getAssets().open(fileName);
            if (fin == null) {
                return null;
            }
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            return EncodingUtils.getString(buffer, "UTF-8");
        } catch (IOException e) {
            Logger.e(TAG, e.getMessage());
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    Logger.e(TAG, e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * get data from assert
     *
     * @param fileName
     * @param clazz
     * @return class you want to return
     */
    public static <T> T getDataFromAsset(Context context, String fileName, Class<T> clazz) {
        String content = getAssetContent(context, fileName);
        if(TextUtils.isEmpty(content)){
            return null;
        }

        try {
            return new Gson().fromJson(content, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * get data from assert
     * return AssetUtil.getDataFromAsset("city.json", new TypeReference<List<ProvinceModel>>() {});
     * @param fileName
     * @param typeReference
     * @return class you want to return
     */
    public static <T> T getDataFromAsset(Context context, String fileName, Type typeReference) {
        String content = getAssetContent(context, fileName);
        if(TextUtils.isEmpty(content)){
            return null;
        }

        try {
            return new Gson().fromJson(content, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
