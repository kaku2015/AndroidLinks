package com.dahuo.sunflower.links.base;

import android.os.Bundle;

import com.dahuo.sunflower.links.common.Logger;


/**
 * 只是用在 ViewPage 的 Fragment
 * @author YanLu
 * @since 17/03/22
 */

public abstract class LazyFragment extends BaseFragment {
    // 用来判断 可见 和 加载数据 状态
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    protected boolean isRegistered = false;

    // 界面对用户 可见/不可见 (用于加载数据)
    public abstract void onVisibleFragment();
    public abstract void onInvisibleFragment();

    // 注册/注销 事件(比如 sub)
    public void onRegister() {}

    public void onUnregister() {}


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareVisibleFragment();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (getActivity() != null) {
            onUserVisible(isVisibleToUser);
        }
        prepareVisibleFragment();
    }


    protected void onUserVisible(boolean visible) {
        if (visible) {
            Logger.d("LazyFragment", "onUserVisible(true)");
            tryRegister();
        } else {
            onInvisibleFragment();
            tryUnregister();
            Logger.d("LazyFragment", "onUserVisible(false)");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        tryRegister();
    }

    @Override
    public void onPause() {
        super.onPause();
        tryUnregister();
    }

    private void tryRegister(){
        if(isVisibleToUser && isViewInitiated && !isRegistered){
            isRegistered = true;
            onRegister();
        }
    }
    private void tryUnregister(){
        Logger.d("LazyFragment", "tryUnregister()");
        if(isRegistered) {
            isRegistered = false;
            onUnregister();
        }
    }

    private boolean prepareVisibleFragment() {
        return prepareVisibleFragment(false);
    }

    public boolean prepareVisibleFragment(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            onVisibleFragment();
            tryRegister();
            isDataInitiated = true;
            return true;
        }
        return false;
    }
}
