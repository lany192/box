package com.github.lany192.arch.items;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.github.lany192.arch.R;
import com.github.lany192.arch.activity.VMVBActivity;
import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.view.DefaultView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

public abstract class ItemsActivity<VM extends ItemsViewModel, CVB extends ViewBinding, TVB extends ViewBinding>
        extends VMVBActivity<VM, CVB, TVB> {
    private final BinderAdapter itemsAdapter = new BinderAdapter();

    public abstract SmartRefreshLayout getRefreshLayout();

    public abstract RecyclerView getRecyclerView();

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, getSpanCount());
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        return layoutManager;
    }

    public <T, B extends ViewBinding> void register(ItemBinder<T, B> binder) {
        itemsAdapter.addItemBinder(binder.getClass(0), binder);
    }

    public int getSpanCount() {
        return 2;
    }

    public int getItemSpanSize(int viewType, int position) {
        return getSpanCount();
    }

    public RecyclerView.ItemDecoration getItemDecoration(RecyclerView.Adapter<?> adapter) {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsAdapter.getLoadMoreModule().setEnableLoadMore(viewModel.loadMoreEnable());
        if (viewModel.loadMoreEnable()) {
            itemsAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
                if (itemsAdapter.getLoadMoreModule().isEnableLoadMore()) {
                    viewModel.onLoadMore();
                }
            });
        }
        if (getRecyclerView().getItemDecorationCount() < 1 && getItemDecoration(itemsAdapter) != null) {
            getRecyclerView().addItemDecoration(getItemDecoration(itemsAdapter));
        }
        itemsAdapter.setGridSpanSizeLookup((gridLayoutManager, viewType, position) -> getItemSpanSize(viewType, position));

        getRecyclerView().setLayoutManager(getLayoutManager());
        getRecyclerView().setAdapter(itemsAdapter);

        getRefreshLayout().setEnableLoadMore(false);
        getRefreshLayout().setOnRefreshListener(refreshLayout -> viewModel.onRefresh());
        //列表状态观察
        viewModel.getListState().observe(this, state -> {
            switch (state) {
                case ERROR:
                    getRefreshLayout().finishRefresh();
                    itemsAdapter.loadMoreFail();
                    break;
                case REFRESHING:
                    break;
                case REFRESH_FINISH:
                    getRefreshLayout().finishRefresh();
                    break;
                case MORE_LOADING:
                    break;
                case MORE_LOAD_END:
                    getRefreshLayout().finishRefresh();
                    itemsAdapter.loadMoreEnd();
                    break;
                case MORE_LOAD_FINISH:
                    getRefreshLayout().finishRefresh();
                    itemsAdapter.loadMoreComplete();
                    break;
            }
        });
        viewModel.getItems().observe(this, data -> {
            itemsAdapter.setList(data.getItems());
            if (ListUtils.isEmpty(data.getItems())) {
                itemsAdapter.setEmptyView(getEmptyView());
            }
        });
    }

    @NonNull
    public View getEmptyView() {
        DefaultView view = new DefaultView(this);
        view.setImage(R.drawable.default_empty);
        view.setMessage(R.string.default_empty);

        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }

    @NonNull
    public View getNetworkView() {
        DefaultView view = new DefaultView(this);
        view.setImage(R.drawable.default_network);
        view.setMessage(R.string.default_network);

        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }

    @NonNull
    public View getErrorView() {
        DefaultView view = new DefaultView(this);
        view.setImage(R.drawable.default_error);
        view.setMessage(R.string.default_error);

        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }
}