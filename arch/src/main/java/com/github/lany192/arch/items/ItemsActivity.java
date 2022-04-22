package com.github.lany192.arch.items;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.github.lany192.arch.R;
import com.github.lany192.arch.activity.ModelBindingActivity;
import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.view.DefaultView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

public abstract class ItemsActivity<VM extends ItemsViewModel, CVB extends ViewBinding, TVB extends ViewBinding>
        extends ModelBindingActivity<VM, CVB, TVB> {
    private final ItemsAdapter itemsAdapter = new ItemsAdapter();

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
        if (getRecyclerView().getItemDecorationCount() < 1 && getItemDecoration(itemsAdapter) != null) {
            getRecyclerView().addItemDecoration(getItemDecoration(itemsAdapter));
        }
        itemsAdapter.setGridSpanSizeLookup((gridLayoutManager, viewType, position) -> getItemSpanSize(viewType, position));

        getRecyclerView().setLayoutManager(getLayoutManager());
        getRecyclerView().setAdapter(itemsAdapter);

        getRefreshLayout().setEnableLoadMore(false);
        getRefreshLayout().setOnRefreshListener(refreshLayout -> viewModel.onRefresh());
        itemsAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> viewModel.onLoadMore());
        //Loading对话框状态观察
        viewModel.getLoadingState().observe(this, show -> {
            if (show) {
                showLoadingDialog();
            } else {
                cancelLoadingDialog();
            }
        });
        //页面基础状态观察
        viewModel.getViewState().observe(this, state -> {
            switch (state) {
                case CONTENT:
                    showContentView();
                    break;
                case ERROR:
                    showErrorView();
                    break;
                case EMPTY:
                    showEmptyView();
                    break;
                case LOADING:
                    showLoadingView();
                    break;
                case NETWORK:
                    showNetworkView();
                    break;
            }
        });
        //列表状态观察
        viewModel.getListState().observe(this, state -> {
            switch (state) {
                case STOP_REQUEST:
                    getRefreshLayout().finishRefresh();
                    itemsAdapter.getLoadMoreModule().loadMoreFail();
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
                case MORE_LOAD_ERROR:
                    getRefreshLayout().finishRefresh();
                    itemsAdapter.loadMoreFail();
                    break;
                case MORE_LOAD_FINISH:
                    getRefreshLayout().finishRefresh();
                    itemsAdapter.loadMoreComplete();
                    break;
            }
        });
        viewModel.getItems().observe(this, data -> {
            itemsAdapter.setNewInstance(data.getItems());
            if (ListUtils.isEmpty(data.getItems())) {
                itemsAdapter.setEmptyView(getEmptyView());
            }
        });
    }

    @NonNull
    public View getEmptyView() {
        DefaultView view = new DefaultView(this);
        view.setImage(R.drawable.data_empty);
        view.setMessage("没有发现数据");
        view.setHint("重新点击试试");
        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }

    @NonNull
    public View getNetworkView() {
        DefaultView view = new DefaultView(this);
        view.setImage(R.drawable.wifi_error);
        view.setMessage("当前网络异常");
        view.setHint("重新点击试试");
        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }

    @NonNull
    public View getErrorView() {
        DefaultView view = new DefaultView(this);
        view.setImage(R.drawable.warning);
        view.setMessage("未知错误");
        view.setHint("重新点击试试");
        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }
}