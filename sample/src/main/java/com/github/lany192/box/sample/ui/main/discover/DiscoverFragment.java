package com.github.lany192.box.sample.ui.main.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel(DiscoverViewModel.class);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        DiscoverAdapter adapter = new DiscoverAdapter(new ArrayList<>());

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addItemDecoration(new ItemDecoration() {
            @Override
            public Divider getDivider(int position) {
                if (position % 2 == 0) {
                    return new Divider.Builder()
                            .setBottomWidth(6)
                            .setLeftWidth(6)
                            .build();
                } else {
                    return new Divider.Builder()
                            .setBottomWidth(6)
                            .setRightWidth(6)
                            .build();
                }
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