package com.lany.box.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<TabItem> tabs;

    public ViewPagerAdapter(FragmentManager fm, List<TabItem> items) {
        super(fm);
        this.tabs = items;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = tabs.get(position).title;
        if (TextUtils.isEmpty(title)) {
            return tabs.get(position).getClass().getSimpleName();
        } else {
            return tabs.get(position).title;
        }
    }

    @Override
    public int getCount() {
        return tabs != null ? tabs.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position).fragment;
    }

    @Setter
    @Getter
    public class TabItem {
        private String title;
        private Fragment fragment;
    }
}
