package com.dahuo.sunflower.links.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author YanLu
 * @since 17/3/26
 */

public class AdResponse extends BaseModel {
    @SerializedName("code")
    public int code;

    public boolean isSuccess() {
        return code == 100;
    }
}
