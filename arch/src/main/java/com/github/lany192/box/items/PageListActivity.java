package com.github.lany192.box.items;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lany192.box.R;
import com.github.lany192.box.activity.BindingActivity;
import com.github.lany192.box.databinding.FragmentPageBinding;
import com.github.lany192.box.utils.ListUtils;
import com.github.lany192.box.view.EmptyView;
import com.github.lany192.box.view.NetworkView;
import com.github.lany192.utils.NetUtils;
import com.hjq.toast.ToastUtils;

import java.lang.reflect.ParameterizedType;

public abstract class PageListActivity<VM extends PageListViewModel>
        extends BindingActivity<FragmentPageBinding> {
    private final BinderAdapter binderAdapter = new BinderAdapter();
    protected VM viewModel;

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

        binderAdapter.setGridSpanSizeLookup((gridLayoutManager, viewType, position) -> getItemSpanSize(viewType, position));

        binding.recyclerView.setLayoutManager(getLayoutManager());
        binding.recyclerView.setAdapter(binderAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            if (NetUtils.isAvailable(this)) {
                viewModel.onRefresh();
            } else {
                binding.refreshLayout.finishRefresh();
                showNetView();
            }
        });
        binderAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            if (NetUtils.isAvailable(this)) {
                viewModel.onLoadMore();
            } else {
                ToastUtils.show("网络异常");
                binderAdapter.getLoadMoreModule().loadMoreComplete();
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
            if (data.isRefresh()) {
                if (ListUtils.isEmpty(data.getItems())) {
                    showEmptyView();
                } else {
                    binderAdapter.setNewInstance(data.getItems());
                }
            } else {
                binderAdapter.addData(data.getItems());
            }
        });

        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                binderAdapter.setEmptyView(R.layout.view_loading);
            }
        });
    }

    private void showNetView() {
        NetworkView emptyView = new NetworkView(this);
        emptyView.setMessage("当前网络异常");
        emptyView.setHint("重新点击试试");
        emptyView.setOnRetryListener(() -> viewModel.onLazyLoad());
        binderAdapter.setEmptyView(emptyView);
    }

    private void showEmptyView() {
        EmptyView emptyView = new EmptyView(this);
        emptyView.setMessage("没有发现数据");
        emptyView.setHint("重新点击试试");
        emptyView.setOnRetryListener(() -> viewModel.onLazyLoad());
        binderAdapter.setEmptyView(emptyView);
    }
}