package com.dahuo.sunflower.links.network;


import com.dahuo.sunflower.links.common.Logger;
import com.dahuo.sunflower.links.model.AppsResponse;
import com.dahuo.sunflower.links.network.api.LinksApiService;
import com.dahuo.sunflower.links.network.retrofit.ApiCallback;
import com.dahuo.sunflower.links.network.retrofit.RetrofitCallback;
import com.dahuo.sunflower.links.network.retrofit.RetrofitFactory;

import retrofit2.Call;

/**
 * @author YanLu
 * @since 17/10/15
 */

public class XadApi {
    private static LinksApiService sApiService;

    /**
     * 应用推荐 展示排名
     */
    public static Call<AppsResponse> getTopApps(final ApiCallback<AppsResponse> apiCallback) {
        return getTopApps(100, 0, apiCallback);
    }
    public static Call<AppsResponse> getTopApps(int limit, int offset,
                                                      final ApiCallback<AppsResponse> apiCallback) {
        Call<AppsResponse> call = getApiService().getTopApps(limit, offset);
        call.enqueue(new RetrofitCallback<>(apiCallback));
        return call;
    }

    /**
     * 应用推荐 列表
     */
    public static Call<AppsResponse> getRecommendApps(final ApiCallback<AppsResponse> apiCallback) {
        return getRecommendApps(100, 0, apiCallback);
    }
    public static Call<AppsResponse> getRecommendApps(int limit, int offset,
                                                      final ApiCallback<AppsResponse> apiCallback) {
        Call<AppsResponse> call = getApiService().getTopApps(limit, offset);
        call.enqueue(new RetrofitCallback<>(apiCallback));
        return call;
    }


    private static LinksApiService getApiService() {
        if(sApiService == null) {
            sApiService = RetrofitFactory.createService(LinksApiService.class, Logger.isEnabled());
        }

        return sApiService;
    }
}
