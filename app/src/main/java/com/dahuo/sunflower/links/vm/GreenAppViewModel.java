package com.dahuo.sunflower.links.vm;


import com.dahuo.sunflower.links.R;
import com.dahuo.sunflower.links.model.RecommendInfo;

/**
 * @author YanLu
 * @since 17/10/29
 */

public class GreenAppViewModel extends BaseViewModel {

    public RecommendInfo app;

    public GreenAppViewModel(RecommendInfo app) {
        this.app = app;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.rv_iv_green_app;
    }
}
