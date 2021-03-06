package com.dahuo.sunflower.links.ui;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dahuo.sunflower.links.R;
import com.dahuo.sunflower.links.base.BaseActivity;
import com.dahuo.sunflower.links.common.Constants;
import com.dahuo.sunflower.links.common.Toasts;
import com.dahuo.sunflower.links.utils.Nav;


/**
 * @author YanLu
 * @since 17/6/4
 */

public class WebActivity extends BaseActivity {
    private WebView mWebView;
    private String mUrl;
    private String mTitle;

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_webview);

        mWebView = findViewById(R.id.web_view);

        mUrl = getExtraValue(String.class, Constants.URL_KEY);
        mTitle = getExtraValue(String.class, Constants.TITLE_KEY);
        if (TextUtils.isEmpty(mUrl)) {
            finish();
        } else {
            onWebViewSetting();
            mWebView.loadUrl(mUrl);
        }
    }

    public  String getToolbarTitle() {
        if(TextUtils.isEmpty(mTitle)) {
            return getString(R.string.app_name);
        } else {
            return mTitle;
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (!TextUtils.isEmpty(mTitle)) {
            setTitle(mTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_browser: {
                Nav.openWithBrowser(this, mUrl);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void onWebViewSetting() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//解决图片不显示
        mWebView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if (uri.getHost().contains(Constants.COOL_APK_DL)
                        || uri.getHost().contains(Constants.COOL_APK_DL_CND)) {
                    // 提示 使用 浏览器打开
                    Toasts.show(R.string.open_with_browser);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgressDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(TextUtils.isEmpty(mTitle)) {
                    setTitle(view.getTitle());
                }
                dismissProgressDialog();
            }
        });

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
