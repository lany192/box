package com.lany.box.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> tabs;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> items) {
        super(fm);
        this.tabs = items;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getClass().getSimpleName();
    }

    @Override
    public int getCount() {
        return tabs != null ? tabs.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }
}
