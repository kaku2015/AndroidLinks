package com.dahuo.sunflower.links.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.dahuo.sunflower.links.ui.BaseHomeAct;


public abstract class BaseFragment extends Fragment {
    public static final String TAG = "BaseFragment";


    /**
     * create fragment instance
     * @param fragmentClazz
     * @param args
     * @param <T>
     * @return
     */
    public static <T extends BaseFragment> T newInstance(Class<T> fragmentClazz, Bundle args) {
        T fragment = null;
        try {
            fragment = fragmentClazz.newInstance();
            fragment.setArguments(args);
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @SuppressWarnings("unchecked")
    public <T> T getArgumentByKey(String key) {
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(key)) {
            return (T) bundle.getSerializable(key);
        }

        return null;
    }


    @SuppressWarnings("unchecked")
    public <T> T getExtraValue(Class<T> tClass, String key) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            try {
                if (tClass.isAssignableFrom(Boolean.class)) {
                    return (T) Boolean.valueOf(bundle.getBoolean(key, false));
                } else if (tClass.isAssignableFrom(Integer.class)) {
                    return (T) Integer.valueOf(bundle.getInt(key, -1));
                } else if (tClass.isAssignableFrom(Long.class)) {
                    return (T) Long.valueOf(bundle.getLong(key, -1));
                } else if (tClass.isAssignableFrom(Float.class)) {
                    return (T) Float.valueOf(bundle.getFloat(key, -1F));
                } else if (tClass.isAssignableFrom(Double.class)) {
                    return (T) Double.valueOf(bundle.getDouble(key, -1D));
                } else if (tClass.isAssignableFrom(String.class)) {
                    if(!TextUtils.isEmpty(bundle.getString(key))) {
                        return (T) String.valueOf(bundle.getString(key));
                    } else {
                        return null;
                    }
                } else if (tClass.isAssignableFrom(Parcelable.class))  {
                    // try to Parcelable
                    return (T) (bundle.getParcelable(key));
                } else  {
                    // try to Serializable
                    return (T) (bundle.getSerializable(key));
                }
            } catch (Exception e) {
                // adIgnore
            }
        }

        return null;
    }

    public BaseFragment() {
    }


    protected void showProgressDialog() {
        if(getActivity() instanceof BaseActivity && isAdded()) {
            ((BaseActivity)(getActivity())).showProgressDialog();
        } else if (getActivity() instanceof BaseHomeAct && isAdded()) {
            ((BaseHomeAct)(getActivity())).showProgressDialog();
        }
    }

    protected void dismissProgressDialog() {
        if (getActivity() instanceof BaseActivity && isAdded()) {
            ((BaseActivity) (getActivity())).dismissProgressDialog();
        } else if (getActivity() instanceof BaseHomeAct && isAdded()) {
            ((BaseHomeAct) (getActivity())).dismissProgressDialog();
        }
    }
}
