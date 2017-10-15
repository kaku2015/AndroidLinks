package com.dahuo.sunflower.links.network.retrofit;

import android.util.Log;

import com.dahuo.sunflower.links.common.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author YanLu
 * @since 17/3/21
 */
public class RetrofitCallback<T> implements Callback<T> {
    private static final String TAG = RetrofitCallback.class.getSimpleName();
    private ApiCallback<T> mListener;

    public RetrofitCallback(ApiCallback<T> listener) {
        mListener = listener;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            if (response.isSuccessful()) {
                mListener.onResponse(response.body());
            } else {
                String errorString = response.errorBody().string();
                mListener.onFailure(new ApiError("HTTP onResponse()", errorString));
            }
        } catch (Exception e) {
            e.printStackTrace();
            mListener.onFailure(new ApiError("HTTP onResponse()", e.getMessage()));
        } finally {
            mListener.onFinish();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if(call != null && !call.isCanceled()) {
            // 可以对t 做些处理
            mListener.onFailure(new ApiError("HTTP onFailure()", t.getMessage()));
            Logger.e(TAG, Log.getStackTraceString(t));
        }
        mListener.onFinish();
    }
}