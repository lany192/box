package com.github.lany192.box.tab;

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

/**
 * 简化ViewPager2和TabLayout的绑定
 */
public class TabPager {
    private final ViewPager2 viewPager2;
    private final TabLayout tabLayout;
    private final List<TabItem> items = new ArrayList<>();
    private FragmentStateAdapter adapter;

    public TabPager(FragmentActivity fragmentActivity, final ViewPager2 viewPager2, final TabLayout tabLayout) {
        this.viewPager2 = viewPager2;
        this.tabLayout = tabLayout;
        adapter = new FragmentStateAdapter(fragmentActivity) {

            @Override
            public int getItemCount() {
                return items.size();
            }

            @NotNull
            @Override
            public Fragment createFragment(int position) {
                return items.get(position).getFragment();
            }
        };
        bind();
    }

    public TabPager(Fragment fragment, final ViewPager2 viewPager2, final TabLayout tabLayout) {
        this.viewPager2 = viewPager2;
        this.tabLayout = tabLayout;
        adapter = new FragmentStateAdapter(fragment) {

            @Override
            public int getItemCount() {
                return items.size();
            }

            @NotNull
            @Override
            public Fragment createFragment(int position) {
                return items.get(position).getFragment();
            }
        };
        bind();
    }

    /**
     * 添加tab
     *
     * @param title    tab标题
     * @param fragment tab内容
     * @return TabPager
     */
    public TabPager addTab(String title, Fragment fragment) {
        if (TextUtils.isEmpty(title)) {
            throw new RuntimeException("addTab: 添加tab失败，title不能为空！");
        }
        if (fragment == null) {
            throw new RuntimeException("addTab: 添加tab失败，fragment不能为null！");
        }
        for (int i = 0; i < items.size(); i++) {
            if (title.equals(items.get(i).getTitle())) {
                return this;
            }
        }
        items.add(new TabItem(title, fragment));
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        return this;
    }

    /**
     * 根据tab名称移除tab
     *
     * @param title tab标题
     */
    public void removeTab(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        Iterator<TabItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            TabItem item = iterator.next();
            if (title.equals(item.getTitle())) {
                iterator.remove();
            }
        }
        if (adapter == null) {
            throw new RuntimeException("adapter不能为null！先调用build");
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 根据tab position移除tab
     */
    public void removeTab(int position) {
        if (position < items.size()) {
            items.remove(position);
        }
        if (adapter == null) {
            throw new RuntimeException("adapter不能为null！先调用build");
        }
        adapter.notifyDataSetChanged();
    }

    private void bind() {
        if (items.size() > 3) {
            this.viewPager2.setOffscreenPageLimit(3);
        }
        this.viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(items.get(position).getTitle());
        }).attach();
    }
}
