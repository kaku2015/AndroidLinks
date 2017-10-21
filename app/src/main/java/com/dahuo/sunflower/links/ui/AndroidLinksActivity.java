package com.dahuo.sunflower.links.ui;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dahuo.sunflower.links.BuildConfig;
import com.dahuo.sunflower.links.R;

import me.drakeet.multitype.Items;
import me.drakeet.support.about.AbsAboutActivity;
import me.drakeet.support.about.OnRecommendedClickedListener;
import me.drakeet.support.about.Recommended;
import me.drakeet.support.about.extension.RecommendedLoaderDelegate;
import me.drakeet.support.about.provided.PicassoImageLoader;


/**
 * Android 应用友链
 *
 * @author YanLu
 * @since 17/10/21
 */

public class AndroidLinksActivity extends AbsAboutActivity implements OnRecommendedClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImageLoader(new PicassoImageLoader());
        setOnRecommendedClickedListener(this);
    }


    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        icon.setImageResource(R.mipmap.ic_launcher);
        slogan.setText(R.string.app_name);
        version.setText("v" + BuildConfig.VERSION_NAME);
    }


    @Override
    protected void onItemsCreated(@NonNull Items items) {
        // Load more Recommended items from remote server asynchronously
        RecommendedLoaderDelegate.attach(this, items.size());
    }


    @Override
    public boolean onRecommendedClicked(@NonNull View itemView, @NonNull Recommended recommended) {
        if(recommended.openWithGooglePlay) {
            openMarket(this, recommended.packageName, recommended.downloadUrl);
        } else {
            openWithBrowser(this, recommended.downloadUrl);
        }
        return false;
    }


    private void openMarket(@NonNull Context context, @NonNull String targetPackage, @NonNull String defaultDownloadUrl) {
        try {
            Intent googlePlayIntent = context.getPackageManager().getLaunchIntentForPackage("com.android.vending");
            ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity");
            // noinspection ConstantConditions
            googlePlayIntent.setComponent(comp);
            googlePlayIntent.setData(Uri.parse("market://details?id=" + targetPackage));
            context.startActivity(googlePlayIntent);
        } catch (Throwable e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(defaultDownloadUrl)));
            e.printStackTrace();
        }
    }

    public static void openWithBrowser(Context context, String url) {
        //在浏览器打开
        try {
            context.startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(url)),
                    context.getString(R.string.chooser_browser)));
        } catch (ActivityNotFoundException e) {
            // nop
        }
    }
}
