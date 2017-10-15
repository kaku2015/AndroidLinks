package com.dahuo.sunflower.links.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.Window;

import com.dahuo.sunflower.links.AndroidApp;
import com.dahuo.sunflower.links.R;
import com.dahuo.sunflower.links.app.AppThemeType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;



public abstract class BaseActivity extends AppCompatActivity {

    public abstract void init(Bundle savedInstanceState);

    public String getToolbarTitle() {
        return getString(R.string.app_name);
    }
    public boolean isDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        // 设置主题
        initAppTheme();
        getWindow().requestFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
        super.onCreate(savedInstanceState);
        init(savedInstanceState);


        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled());
            setTitle(getToolbarTitle());
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initAppTheme() {
        if(AndroidApp.getAppTheme() == AppThemeType.THEME_DARK) {
            setTheme(R.style.AppTheme);
            setMiuiStatusBarDarkMode(this, false);
        } else {
            setTheme(R.style.AppThemeLight);
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


    @SuppressWarnings("unchecked")
    public <T> T getExtraValue(Class<T> tClass, String key) {
        Intent intent = getIntent();
        if (intent != null) {
            try {
                if (tClass.isAssignableFrom(Boolean.class)) {
                    return (T) Boolean.valueOf(intent.getBooleanExtra(key, false));
                } else if (tClass.isAssignableFrom(Integer.class)) {
                    return (T) Integer.valueOf(intent.getIntExtra(key, -1));
                } else if (tClass.isAssignableFrom(Long.class)) {
                    return (T) Long.valueOf(intent.getLongExtra(key, -1));
                } else if (tClass.isAssignableFrom(Float.class)) {
                    return (T) Float.valueOf(intent.getFloatExtra(key, -1F));
                } else if (tClass.isAssignableFrom(Double.class)) {
                    return (T) Double.valueOf(intent.getDoubleExtra(key, -1D));
                } else if (tClass.isAssignableFrom(String.class)) {
                    if (!TextUtils.isEmpty(intent.getStringExtra(key))) {
                        return (T) String.valueOf(intent.getStringExtra(key));
                    } else {
                        return null;
                    }
                } else if (tClass.isAssignableFrom(Parcelable.class)) {
                    // try to Parcelable
                    return (T) (intent.getParcelableExtra(key));
                } else {
                    // try to Serializable
                    return (T) (intent.getSerializableExtra(key));
                }
            } catch (Exception e) {
                // adIgnore
            }
        }

        return null;
    }

    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkMode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkMode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return false;
    }
}
