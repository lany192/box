package com.github.lany192.arch.items;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lany192.arch.R;
import com.github.lany192.arch.activity.BindingActivity;
import com.github.lany192.arch.databinding.ActivityPageBinding;
import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.arch.view.EmptyView;
import com.github.lany192.arch.view.NetworkView;
import com.github.lany192.utils.NetUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;

import java.lang.reflect.ParameterizedType;

public abstract class PageListActivity<VM extends PageListViewModel> extends BindingActivity<ActivityPageBinding> {
    private final BinderAdapter binderAdapter = new BinderAdapter();
    protected VM viewModel;

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .titleBar(binding.toolbar)
                .init();
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, getSpanCount());
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        return layoutManager;
    }

    public void register(ItemBinder binder) {
        binderAdapter.addItemBinder(binder.getTargetClass(), binder);
    }

    public int getSpanCount() {
        return 2;
    }

    public int getItemSpanSize(int viewType, int position) {
        return getSpanCount();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = getViewModel((Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);


        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binderAdapter.setGridSpanSizeLookup((gridLayoutManager, viewType, position) -> getItemSpanSize(viewType, position));

        binding.recyclerView.setLayoutManager(getLayoutManager());
        binding.recyclerView.setAdapter(binderAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            if (NetUtils.isAvailable(this)) {
                viewModel.onRefresh();
            } else {
                binding.refreshLayout.finishRefresh();
                NetworkView networkView = getNetworkView();
                networkView.setOnRetryListener(() -> viewModel.onLazyLoad());
                binderAdapter.setEmptyView(networkView);
            }
        });
        binderAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            if (NetUtils.isAvailable(this)) {
                viewModel.onLoadMore();
            } else {
                ToastUtils.show("网络异常");
                binderAdapter.getLoadMoreModule().loadMoreFail();
//                showNetView();
            }
        });
        viewModel.getRefreshState().observe(this, refreshing -> {
            if (!refreshing) {
                binding.refreshLayout.finishRefresh();
            }
        });
        viewModel.getLoadMoreState().observe(this, moreLoading -> {
            if (!moreLoading) {
                binderAdapter.getLoadMoreModule().loadMoreComplete();
            }
        });
        viewModel.getItems().observe(this, data -> {
            if (ListUtils.isEmpty(data.getItems())) {
                if (NetUtils.isAvailable(this)) {
                    EmptyView emptyView = getEmptyView();
                    emptyView.setOnRetryListener(() -> viewModel.onLazyLoad());
                    binderAdapter.setEmptyView(emptyView);
                } else {
                    NetworkView networkView = getNetworkView();
                    networkView.setOnRetryListener(() -> viewModel.onLazyLoad());
                    binderAdapter.setEmptyView(networkView);
                }
            } else {
                binderAdapter.setNewInstance(data.getItems());
            }
        });
        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                binderAdapter.setEmptyView(R.layout.view_loading);
            }
        });
    }

    @NonNull
    public EmptyView getEmptyView() {
        EmptyView emptyView = new EmptyView(this);
        emptyView.setMessage("没有发现数据");
        emptyView.setHint("重新点击试试");
        return emptyView;
    }

    @NonNull
    public NetworkView getNetworkView() {
        NetworkView networkView = new NetworkView(this);
        networkView.setMessage("当前网络异常");
        networkView.setHint("重新点击试试");
        networkView.setOnRetryListener(() -> viewModel.onLazyLoad());
        return networkView;
    }
}