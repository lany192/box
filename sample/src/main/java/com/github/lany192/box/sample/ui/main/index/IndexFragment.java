package com.github.lany192.box.sample.ui.main.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.box.fragment.BindingFragment;
import com.github.lany192.box.tab.TabAdapter;
import com.github.lany192.box.tab.TabItem;
import com.github.lany192.box.sample.databinding.FragmentIndexBinding;
import com.github.lany192.box.sample.ui.main.index.article.ArticleFragment;
import com.github.lany192.box.sample.ui.main.index.city.CityFragment;
import com.github.lany192.box.sample.ui.main.index.girl.GirlFragment;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IndexFragment extends BindingFragment<FragmentIndexBinding> {
    private IndexViewModel viewModel;

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .navigationBarColor(android.R.color.holo_orange_light)
                .titleBar(binding.tabLayout)
                .init();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel(IndexViewModel.class);

        List<TabItem> items = new ArrayList<>();
        items.add(new TabItem("推荐", new ArticleFragment()));
        items.add(new TabItem("地区", new CityFragment()));
        items.add(new TabItem("图片", new GirlFragment()));

        TabAdapter tabAdapter = new TabAdapter(requireActivity(), items);
        binding.viewpager.setAdapter(tabAdapter);
        binding.tabLayout.setViewPager2(binding.viewpager, tabAdapter.getTitles());
        return root;
    }
}
