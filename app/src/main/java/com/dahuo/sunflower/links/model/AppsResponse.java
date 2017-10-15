package com.dahuo.sunflower.links.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author YanLu
 * @since 17/10/8
 */

public class AppsResponse extends AdResponse {

    @SerializedName("data")
    public List<RecommendInfo> data;
}
