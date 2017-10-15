package com.dahuo.sunflower.links.ui;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.dahuo.sunflower.links.base.BaseActivity;
import com.dahuo.sunflower.links.R;


/**
 * 设置界面 装载 (fragment)
 * @author YanLu
 * @since 16/12/6
 */

public class SettingsActivity extends BaseActivity {
    private static final String TAG = "SettingsActivity";

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_setting);
        if(savedInstanceState == null) {
            SettingsFragment fragment = SettingsFragment.newInstance(null);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commitAllowingStateLoss();
        }
    }

    public  String getToolbarTitle() {
        return getString(R.string.nav_item_setting);
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


}
