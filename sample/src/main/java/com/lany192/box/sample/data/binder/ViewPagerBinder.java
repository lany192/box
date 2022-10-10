package com.lany192.box.sample.data.binder;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.SampleRouter;
import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.arch.tab.TabAdapter;
import com.github.lany192.arch.tab.TabItem;
import com.lany192.box.sample.data.bean.ViewPagerItem;
import com.lany192.box.sample.databinding.ItemViewPagerBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerBinder extends ItemBinder<ViewPagerItem, ItemViewPagerBinding> {

    @Override
    public void convert(@NonNull ItemViewPagerBinding binding, ViewPagerItem item, int position) {
        List<TabItem> items = new ArrayList<>();
        items.add(new TabItem("标题1", SampleRouter.getArticle()));
        items.add(new TabItem("标题1", SampleRouter.getArticle()));
        TabAdapter adapter = new TabAdapter((FragmentActivity) getContext(), items);
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setViewPager(binding.viewPager, adapter.getTitles());
    }
}
