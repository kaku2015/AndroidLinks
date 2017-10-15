package com.dahuo.sunflower.links.ui;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.dahuo.sunflower.links.base.BaseActivity;
import com.dahuo.sunflower.links.BuildConfig;
import com.dahuo.sunflower.links.R;


/**
 * 关于
 *
 * @author YanLu
 * @since 17/10/15
 */

public class AboutActivity extends BaseActivity {
    private static final String TAG = "AboutActivity";

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.frg_about);
        TextView html = findViewById(R.id.tv_html);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            html.setText(Html.fromHtml(getString(R.string.about_app), Html.FROM_HTML_MODE_LEGACY));
        } else {
            html.setText(Html.fromHtml(getString(R.string.about_app)));
        }
        html.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public  String getToolbarTitle() {
        return getString(R.string.app_version_about, BuildConfig.VERSION_NAME);
    }
}
