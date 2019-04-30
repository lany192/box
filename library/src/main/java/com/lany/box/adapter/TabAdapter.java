package com.lany.box.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import java.util.List;

public class TabAdapter extends FragmentStatePagerAdapter {
    private List<TabItem> tabs;

    public TabAdapter(FragmentManager fm, List<TabItem> items) {
        super(fm);
        this.tabs = items;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = tabs.get(position).getTitle();
        if (TextUtils.isEmpty(title)) {
            return tabs.get(position).getClass().getSimpleName();
        } else {
            return tabs.get(position).getTitle();
        }
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
