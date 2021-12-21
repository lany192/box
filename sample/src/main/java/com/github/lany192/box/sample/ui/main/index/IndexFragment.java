package com.github.lany192.box.sample.ui.main.index;

import com.alibaba.android.arouter.AppRouter;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.fragment.BindingFragment;
import com.github.lany192.arch.tab.TabAdapter;
import com.github.lany192.arch.tab.TabItem;
import com.github.lany192.box.sample.databinding.FragmentIndexBinding;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/fragment/index")
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

    @Override
    public void initView() {
        viewModel = getFragmentViewModel(IndexViewModel.class);

        List<TabItem> items = new ArrayList<>();
        items.add(new TabItem("推荐", AppRouter.get().getArticle()));
        items.add(new TabItem("地区", AppRouter.get().getCity()));
        items.add(new TabItem("图片", AppRouter.get().getGirl()));

        TabAdapter tabAdapter = new TabAdapter(requireActivity(), items);
        binding.viewpager.setAdapter(tabAdapter);
        binding.tabLayout.setViewPager2(binding.viewpager, tabAdapter.getTitles());
    }
}
