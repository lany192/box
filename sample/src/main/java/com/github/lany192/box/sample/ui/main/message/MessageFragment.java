package com.github.lany192.box.sample.ui.main.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.arch.fragment.BindingFragment;
import com.github.lany192.arch.tab.TabAdapter;
import com.github.lany192.arch.tab.TabItem;
import com.github.lany192.box.sample.databinding.FragmentMessageBinding;
import com.github.lany192.box.sample.ui.main.index.article.ArticleFragment;
import com.github.lany192.box.sample.ui.main.index.city.CityFragment;
import com.github.lany192.box.sample.ui.main.index.girl.GirlFragment;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MessageFragment extends BindingFragment<FragmentMessageBinding> {
    private MessageViewModel viewModel;

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .navigationBarColor(android.R.color.white)
                .navigationBarDarkIcon(true)
                .titleBar(binding.toolbar)
                .init();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel(MessageViewModel.class);

        List<TabItem> items = new ArrayList<>();
        items.add(new TabItem("互动", new ArticleFragment()));
        items.add(new TabItem("系统消息", new CityFragment()));
        items.add(new TabItem("游戏通知", new GirlFragment()));

        TabAdapter tabAdapter = new TabAdapter(requireActivity(), items);
        binding.viewpager.setAdapter(tabAdapter);
        binding.tabLayout.setViewPager2(binding.viewpager, tabAdapter.getTitles());
        return root;
    }

}
