package com.github.lany192.box.fragment;

import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FragmentPager {
    private List<PagerItem> items = new ArrayList<>();
    private final ViewPager2 viewPager2;
    private final TabLayout tabLayout;

    public FragmentPager(final ViewPager2 viewPager2, final TabLayout tabLayout) {
        this.viewPager2 = viewPager2;
        this.tabLayout = tabLayout;
    }

    public FragmentPager addTab(String title, Fragment fragment) {
        if (TextUtils.isEmpty(title)) {
            throw new RuntimeException("addTab: 添加tab失败，title不能为空！");
        }
        if (fragment == null) {
            throw new RuntimeException("addTab: 添加tab失败，fragment不能为null！");
        }
        for (int i = 0; i < items.size(); i++) {
            if (title.equals(items.get(i).title)) {
                return this;
            }
        }
        items.add(new PagerItem(title, fragment));
        return this;
    }

    public void removeTab(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        Iterator<PagerItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            PagerItem item = iterator.next();
            if (title.equals(item.title)) {
                iterator.remove();
            }
        }
    }

    public void removeTab(int position) {
        if (position < items.size()) {
            items.remove(position);
        }
    }

    public void build(FragmentActivity fragmentActivity) {
        bind(new FragmentStateAdapter(fragmentActivity) {

            @Override
            public int getItemCount() {
                return items.size();
            }

            @NotNull
            @Override
            public Fragment createFragment(int position) {
                return items.get(position).fragment;
            }
        });
    }

    public void build(Fragment fragment) {
        bind(new FragmentStateAdapter(fragment) {

            @Override
            public int getItemCount() {
                return items.size();
            }

            @NotNull
            @Override
            public Fragment createFragment(int position) {
                return items.get(position).fragment;
            }
        });
    }

    private void bind(FragmentStateAdapter adapter) {
        if (items.size() > 3) {
            this.viewPager2.setOffscreenPageLimit(3);
        }
        this.viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(items.get(position).title);
        }).attach();
    }

    public static class PagerItem {
        String title;
        Fragment fragment;

        public PagerItem(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }
    }
}
