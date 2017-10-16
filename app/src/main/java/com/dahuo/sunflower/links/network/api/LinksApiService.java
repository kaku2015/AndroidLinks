package com.dahuo.sunflower.links.network.api;

import com.dahuo.sunflower.links.model.AppsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author YanLu
 * @since 17/10/15
 */

public interface LinksApiService {
    @GET("v2/app_recommend/top/pull")
    Call<AppsResponse> getTopApps(@Query("limit") int limit,
                                  @Query("offset") int offset);

    @GET("v2/app_recommend/pull")
    Call<AppsResponse> getRecommendApps(@Query("limit") int limit,
                                        @Query("offset") int offset,
                                        @Query("package_name") String pkgName);
}
