package com.github.lany192.box.adapter;

import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class FragmentsAdapter extends FragmentStatePagerAdapter {
    private List<TabItem> tabs;

    public FragmentsAdapter(FragmentManager fm, List<TabItem> items) {
        //要实现fragment懒加载功能，主要看第二个参数，然后在Fragment的OnResume里判断是否可见
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
