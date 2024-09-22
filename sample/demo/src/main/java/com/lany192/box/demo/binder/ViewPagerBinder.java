package com.lany192.box.demo.binder;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.arch.tab.TabAdapter;
import com.github.lany192.arch.tab.TabItem;
import com.lany192.box.demo.ui.main.index.article.ArticleBuilder;
import com.lany192.box.network.data.bean.ViewPagerItem;
import com.lany192.box.demo.databinding.ItemViewPagerBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerBinder extends ItemBinder<ViewPagerItem, ItemViewPagerBinding> {

    @Override
    public boolean isFullSpanItem(int itemType) {
        return true;
    }

    @Override
    public void convert(@NonNull ItemViewPagerBinding binding, ViewPagerItem item, int position) {
        List<TabItem> items = new ArrayList<>();
        items.add(new TabItem("标题1", ArticleBuilder.getFragment()));
        items.add(new TabItem("标题2", ArticleBuilder.getFragment()));
        TabAdapter adapter = new TabAdapter((FragmentActivity) getContext(), items);
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setViewPager(binding.viewPager, adapter.getTitles());
    }
}
