package com.dahuo.sunflower.links.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YanLu
 * @since 16/5/13
 */
public abstract class BaseFragmentStatePagerAdapter<T extends Serializable> extends FragmentStatePagerAdapter {
    protected ArrayList<T> mData = new ArrayList<>();
    protected SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public BaseFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mData.size();
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

//    public Fragment getRegisteredFragment(int position) {
//        return registeredFragments.get(position);
//    }

    public SparseArray<Fragment> getAllFragments() {
        return registeredFragments;
    }


    public void addItem(final T item) {
        mData.add(item);
        notifyDataSetChanged();
    }
    public void addAll(final List<T> items) {
        if (items != null) {
            mData.addAll(items);
        }
        notifyDataSetChanged();
    }
    public void setData(final List<T> items) {
        mData.clear();
        if (items != null) {
            mData.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void addItem(int idx, final T item) {
        mData.add(idx, item);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mData.clear();
        notifyDataSetChanged();
    }

    public T getItemData(int position) {
        return mData.get(position);
    }

    public ArrayList<T> getData() {
        return mData;
    }
}
