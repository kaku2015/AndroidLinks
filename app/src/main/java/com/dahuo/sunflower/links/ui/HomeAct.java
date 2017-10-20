package com.dahuo.sunflower.links.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.dahuo.sunflower.links.AndroidApp;
import com.dahuo.sunflower.links.base.BaseFragmentStatePagerAdapter;
import com.dahuo.sunflower.links.common.Constants;
import com.dahuo.sunflower.links.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeAct extends BaseHomeAct {

    @Override
    public void init(Bundle savedInstanceState) {
        RulesPagerAdapter<String> pagerAdapter = new RulesPagerAdapter<>(getSupportFragmentManager());
        initTabs();
        pagerAdapter.setData(mDataList);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        pagerAdapter.notifyDataSetChanged();

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mNavigationView != null) {
            mNavigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                onCloseDrawer();
                break;
            case R.id.nav_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, Constants.APP_SETTING);
                break;
            case R.id.nav_about:
                onAbout();
                break;
            default:
                break;

        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        onCloseDrawer();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.APP_SETTING) {
            if (AndroidApp.isThemeChange()) {
                reload();
            }
        }
    }

    private void initTabs() {
        mDataList.clear();
        mDataList.add(getString(R.string.tab_top_apps));
        mDataList.add(getString(R.string.tab_recommend_apps));
        mDataList.add(getString(R.string.tab_green_apps));
    }


    protected List<String> mDataList = new ArrayList<>();
    private class RulesPagerAdapter<T extends Serializable> extends BaseFragmentStatePagerAdapter<T> {


        public RulesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putSerializable(Constants.APP_TAB_KEY, getItemData(position));
            switch (position) {
                case 0:
                    return TopAppFragment.newInstance(TopAppFragment.class, args);
                case 1:
                    return RecommendAppFragment.newInstance(RecommendAppFragment.class, args);
                case 2:
                    return GreenAppFragment.newInstance(GreenAppFragment.class, args);
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            T item = getItemData(position);
            if(item instanceof String) {
                return (String)item;
            } else {
                return position + " ";
            }
        }
    }
}
