package com.dahuo.sunflower.links.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dahuo.sunflower.links.AndroidApp;
import com.dahuo.sunflower.links.base.BaseActivity;
import com.dahuo.sunflower.links.app.AppThemeType;
import com.dahuo.sunflower.links.utils.Nav;
import com.dahuo.sunflower.links.R;


public abstract class BaseHomeAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AppBarLayout.OnOffsetChangedListener {

    public abstract void init(Bundle savedInstanceState);

    protected AppBarLayout mAppBarLayout;
    protected Toolbar mToolbar;
    protected NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    protected ViewPager mViewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 设置主题
        initAppTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        mAppBarLayout = findViewById(R.id.app_bar_main);
        mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mViewPager = findViewById(R.id.vp_app);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mToolbar.setTitle(R.string.app_name);
        mAppBarLayout.addOnOffsetChangedListener(this);

        init(savedInstanceState);
    }


    protected void onAbout() {
        Nav.act(this, AboutActivity.class);
    }

    protected void onCloseDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        if (mNavigationView != null) {
            mNavigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private void initAppTheme() {
        if (AndroidApp.getAppTheme() == AppThemeType.THEME_DARK) {
            setTheme(R.style.AppTheme_NoActionBar);
            setMiuiStatusBarDarkMode(this, false);
        } else {
            setTheme(R.style.AppThemeLight_NoActionBar);
            setMiuiStatusBarDarkMode(this, true);
        }
    }

    protected AlertDialog mProgressDialog;

    public void dismissProgressDialog() {
        try {
            if(mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (final Exception e) {
            // Handle or log or adIgnore
        } finally {
            mProgressDialog = null;
        }

    }

    public void showProgressDialog() {
        if (!isFinishing()) {
            try {
                if (mProgressDialog == null) {
                    mProgressDialog = new AlertDialog
                            .Builder(this, R.style.AppAlertDialog)
                            .setView(R.layout.progress_spinner)
                            .setCancelable(true)
                            .create();
                }
                mProgressDialog.show();
            } catch (final Exception e) {
                mProgressDialog = null;
            }
        }
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


    boolean isCanChildScrollUp = false;
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        isCanChildScrollUp = verticalOffset < 0;
    }

    public boolean isCanChildScrollUp () {
        return isCanChildScrollUp;
    }


    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkMode) {
        return BaseActivity.setMiuiStatusBarDarkMode(activity, darkMode);
    }
}
