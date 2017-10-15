package com.dahuo.sunflower.links.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.TextUtils;

import com.dahuo.sunflower.links.AndroidApp;
import com.dahuo.sunflower.links.common.Constants;
import com.dahuo.sunflower.links.R;


/**
 *
 * @author YanLu
 * @since 17/9/16
 */

public class SettingsFragment extends PreferenceFragment {
    private static final String TAG = "SettingsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        initPreference();
    }

    private void initPreference() {
        ListPreference themeCategory = (ListPreference) findPreference(Constants.THEME_KEY);

        themeCategory.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue != null) {
                    if(newValue instanceof String) {
                        String theme = (String) newValue;
                        if(TextUtils.isDigitsOnly(theme)) {
                            AndroidApp.setAppTheme(Integer.valueOf(theme));
                        }
                    }
                    reload();
                }
                return true;
            }
        });



        SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
        prefs.registerOnSharedPreferenceChangeListener(mPreferenceChangeListener);

        mPreferenceChangeListener.onSharedPreferenceChanged(prefs, Constants.THEME_KEY);
    }


    private final SharedPreferences.OnSharedPreferenceChangeListener mPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
                case Constants.THEME_KEY:
                    setPreferenceEntry(key);
                    break;
            }
        }
    };

    public final void setPreferenceSummary(Preference pref, CharSequence value) {
        pref.setSummary(value);
    }

    public final void setPreferenceEntry(String key) {
        ListPreference preference = (ListPreference) findPreference(key);
        setPreferenceSummary(preference, preference.getEntry());
    }

    private void reload() {
        Intent intent = getActivity().getIntent();
        getActivity().overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(intent);

    }

    public static SettingsFragment newInstance(Bundle args) {
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
