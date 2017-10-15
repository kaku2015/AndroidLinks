package com.dahuo.sunflower.links.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author YanLu
 * @since 17/10/15
 */

public class RecommendInfo extends BaseModel {
    @SerializedName("appName")
    public String appName;
    @SerializedName("packageName")
    public String packageName;
    @SerializedName("description")
    public String description;

    @SerializedName("downloadSize")
    public String apkSize;
    @SerializedName("iconUrl")
    public String iconUrl;
    @SerializedName("downloadURL")
    public String downloadUrl;
    @SerializedName("count")
    public int count;
    @SerializedName("openWithGooglePlay")
    public boolean openWithGooglePlay;

    public RecommendInfo() {
    }
}
