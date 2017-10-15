package com.dahuo.sunflower.links.vm;


import com.dahuo.sunflower.links.R;
import com.dahuo.sunflower.links.model.RecommendInfo;

/**
 * @author YanLu
 * @since 17/10/15
 */

public class RecommendAppViewModel extends BaseViewModel {

    public RecommendInfo app;

    public RecommendAppViewModel(RecommendInfo app) {
        this.app = app;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.rv_iv_recommend_app;
    }
}
