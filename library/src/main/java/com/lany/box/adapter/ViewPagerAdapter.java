package com.lany.box.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lany.box.entity.TabItem;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<TabItem> tabs = null;

    public ViewPagerAdapter(FragmentManager fm, List<TabItem> items) {
        super(fm);
        this.tabs = items;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return tabs != null ? tabs.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position).getFragment();
    }
}
