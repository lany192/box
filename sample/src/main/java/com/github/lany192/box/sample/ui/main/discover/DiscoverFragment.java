package com.github.lany192.box.sample.ui.main.discover;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.items.PageListFragment;
import com.github.lany192.box.sample.data.binder.ImageBinder;
import com.github.lany192.box.sample.databinding.FragmentDiscoverBinding;
import com.github.lany192.decoration.Divider;
import com.github.lany192.decoration.ItemDecoration;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/fragment/discover")
public class DiscoverFragment extends PageListFragment<DiscoverViewModel, FragmentDiscoverBinding> {
    {
        register(new ImageBinder());
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .navigationBarColor(android.R.color.holo_red_light)
                .titleBar(binding.toolbar)
                .init();
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        return layoutManager;
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
        binding.recyclerView.addItemDecoration(new ItemDecoration() {
            @Override
            public Divider getDivider(int position) {
                return new Divider().setTopWidth(2).setBottomWidth(2).setLeftWidth(2).setRightWidth(2);
            }
        });
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] first = new int[layoutManager.getSpanCount()];
                layoutManager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    //防止第一行到顶部有空白区域
                    layoutManager.invalidateSpanAssignments();
                }
            }
        });
    }
}