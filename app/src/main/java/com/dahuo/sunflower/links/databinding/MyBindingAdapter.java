package com.dahuo.sunflower.links.databinding;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dahuo.sunflower.links.R;
import com.dahuo.sunflower.links.model.RecommendInfo;
import com.squareup.picasso.Picasso;


/**
 * @author YanLu
 * @since 16/4/27
 */
public class MyBindingAdapter {

    @BindingAdapter("isVisible")
    public static void setIsInvisible(View view, boolean visible){
      view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("isGone")
    public static void setIsGone(View view, boolean gone){
      view.setVisibility(gone ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter({"imageUrl"})
    public static void imageLoader(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }


    @BindingAdapter({"recommendInfo"})
    public static void onRecommend(TextView textView, RecommendInfo info) {
        textView.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(info.packageName) || TextUtils.isEmpty(info.apkSize)) {
            if(!TextUtils.isEmpty(info.packageName)) {
                textView.setText(info.packageName);
            } else {
                textView.setVisibility(View.GONE);
            }
        } else {
            textView.setText(textView.getContext().getString(R.string.recommend_app_info,
                    info.packageName, info.apkSize));
        }
    }
}
