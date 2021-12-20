package com.github.lany192.box.sample.ui.main.discover;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.lany192.arch.fragment.BindingFragment;
import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.arch.view.EmptyView;
import com.github.lany192.arch.view.LoadingView;
import com.github.lany192.box.sample.databinding.FragmentDiscoverBinding;
import com.github.lany192.decoration.Divider;
import com.github.lany192.decoration.ItemDecoration;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DiscoverFragment extends BindingFragment<FragmentDiscoverBinding> {
    private DiscoverViewModel viewModel;

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .navigationBarColor(android.R.color.holo_red_light)
                .titleBar(binding.toolbar)
                .init();
    }

    @Override
    public void initView() {
        viewModel = getFragmentViewModel(DiscoverViewModel.class);
        DiscoverAdapter adapter = new DiscoverAdapter(new ArrayList<>());

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addItemDecoration(new ItemDecoration() {
            @Override
            public Divider getDivider(int position) {
                return new Divider().setTopWidth(2).setBottomWidth(2).setLeftWidth(2).setRightWidth(2);
            }
        });
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] first = new int[layoutManager.getSpanCount()];
                layoutManager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    //防止第一行到顶部有空白区域
                    layoutManager.invalidateSpanAssignments();
                }
            }
        });
        viewModel.getItems().observe(this, strings -> {
            if (ListUtils.isEmpty(strings)) {
                EmptyView emptyView = new EmptyView(requireContext());
                emptyView.setMessage("没有发现数据");
                emptyView.setHint("重新点击试试");
                emptyView.setOnRetryListener(() -> viewModel.retry());
                adapter.setEmptyView(emptyView);
            } else {
                adapter.setNewInstance(strings);
            }
        });
        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                adapter.setEmptyView(new LoadingView(requireContext()));
            }
        });
    }

}