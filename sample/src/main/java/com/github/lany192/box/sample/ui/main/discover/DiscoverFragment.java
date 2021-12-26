package com.github.lany192.box.sample.ui.main.discover;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.items.ListFragment;
import com.github.lany192.box.sample.data.binder.CategoryBinder;
import com.github.lany192.box.sample.data.binder.ImageBinder;
import com.github.lany192.box.sample.databinding.FragmentDiscoverBinding;
import com.github.lany192.decoration.Divider;
import com.github.lany192.decoration.ItemDecoration;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/fragment/discover")
public class DiscoverFragment extends ListFragment<DiscoverViewModel, FragmentDiscoverBinding> {

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .navigationBarColor(android.R.color.holo_red_light)
                .titleBar(binding.toolbar)
                .init();
    }

    @Override
    public int getSpanCount() {
        return 4;
    }

    @Override
    public int getItemSpanSize(int viewType, int position) {
        if (position < 4) {
            return 1;
        }
        return 2;
    }

    @Override
    public SmartRefreshLayout getRefreshLayout() {
        return binding.refreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return binding.recyclerView;
    }

    @Override
    public void initView() {
        super.initView();
        register(new ImageBinder());
        register(new CategoryBinder());
        binding.recyclerView.addItemDecoration(new ItemDecoration() {
            @Override
            public Divider getDivider(int position) {
                return new Divider().setTopWidth(2).setBottomWidth(2).setLeftWidth(2).setRightWidth(2);
            }
        });
    }
}