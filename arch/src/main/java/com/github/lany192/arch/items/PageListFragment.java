package com.github.lany192.arch.items;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.github.lany192.arch.R;
import com.github.lany192.arch.fragment.ViewModelFragment;
import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.arch.view.EmptyView;
import com.github.lany192.arch.view.ErrorView;
import com.github.lany192.arch.view.NetworkView;
import com.github.lany192.utils.NetUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

public abstract class PageListFragment<VM extends PageListViewModel,VB extends ViewBinding>
        extends ViewModelFragment<VM,VB> {
    private final BinderAdapter binderAdapter = new BinderAdapter();

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getSpanCount());
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

    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }
    
    public abstract SmartRefreshLayout getRefreshLayout();
    
    public abstract RecyclerView getRecyclerView();

    @CallSuper
    @Override
    public void initView() {
        super.initView();
        binderAdapter.setGridSpanSizeLookup((gridLayoutManager, viewType, position) -> getItemSpanSize(viewType, position));
        binderAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> viewModel.onLoadMore());

        getRecyclerView().setLayoutManager(getLayoutManager());
        getRecyclerView().setAdapter(binderAdapter);
        if (getRecyclerView().getItemDecorationCount() < 1 && getItemDecoration() != null) {
            getRecyclerView().addItemDecoration(getItemDecoration());
        }
        getRefreshLayout().setEnableLoadMore(false);
        getRefreshLayout().setOnRefreshListener(refreshLayout -> viewModel.onRefresh());
        viewModel.getViewState().observe(this, state -> {
            switch (state) {
                case CONTENT:
                    break;
                case ERROR:
                    binderAdapter.setEmptyView(getErrorView());
                    break;
                case EMPTY:
                    binderAdapter.setEmptyView(getEmptyView());
                    break;
                case LOADING:
                    binderAdapter.setEmptyView(R.layout.view_loading);
                    break;
                case NETWORK:
                    binderAdapter.setEmptyView(getNetworkView());
                    break;
                case SHOW_LOADING_DIALOG:
                    showLoadingDialog();
                    break;
                case CANCEL_LOADING_DIALOG:
                    cancelLoadingDialog();
                    break;
            }
        });
        viewModel.getListState().observe(this, state -> {
            switch (state) {
                case STOP_REQUEST:
                    getRefreshLayout().finishRefresh();
                    binderAdapter.getLoadMoreModule().loadMoreFail();
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
                    binderAdapter.getLoadMoreModule().loadMoreEnd();
                    break;
                case MORE_LOAD_ERROR:
                    getRefreshLayout().finishRefresh();
                    binderAdapter.getLoadMoreModule().loadMoreFail();
                    break;
                case MORE_LOAD_FINISH:
                    getRefreshLayout().finishRefresh();
                    binderAdapter.getLoadMoreModule().loadMoreComplete();
                    break;
            }
        });
        viewModel.getItems().observe(this, data -> {
            if (ListUtils.isEmpty(data.getItems())) {
                if (NetUtils.isAvailable(requireContext())) {
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
    }

    @NonNull
    public EmptyView getEmptyView() {
        EmptyView view = new EmptyView(requireContext());
        view.setMessage("没有发现数据");
        view.setHint("重新点击试试");
        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }

    @NonNull
    public NetworkView getNetworkView() {
        NetworkView view = new NetworkView(requireContext());
        view.setMessage("当前网络异常");
        view.setHint("重新点击试试");
        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }

    @NonNull
    public ErrorView getErrorView() {
        ErrorView view = new ErrorView(requireContext());
        view.setMessage("未知错误");
        view.setHint("重新点击试试");
        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }
}