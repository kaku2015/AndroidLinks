package com.dahuo.sunflower.links.vm;


import com.dahuo.sunflower.links.R;
import com.dahuo.sunflower.links.model.RecommendInfo;

/**
 * @author YanLu
 * @since 17/8/28
 */

public class TopAppViewModel extends BaseViewModel {

    public RecommendInfo app;

    public TopAppViewModel(RecommendInfo app) {
        this.app = app;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.rv_iv_top_app;
    }
}
