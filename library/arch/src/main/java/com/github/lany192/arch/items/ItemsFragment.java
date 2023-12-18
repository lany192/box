package com.github.lany192.arch.items;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter4.QuickAdapterHelper;
import com.chad.library.adapter4.layoutmanager.QuickGridLayoutManager;
import com.chad.library.adapter4.loadState.LoadState;
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter;
import com.github.lany192.arch.R;
import com.github.lany192.arch.adapter.MultiAdapter;
import com.github.lany192.arch.adapter.ItemBinder;
import com.github.lany192.arch.fragment.VMVBFragment;
import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.view.DefaultView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.List;

public abstract class ItemsFragment<VM extends ItemsViewModel, VB extends ViewBinding>
        extends VMVBFragment<VM, VB> {
    private final MultiAdapter mAdapter = new MultiAdapter();
    private QuickAdapterHelper adapterHelper;

    public RecyclerView.LayoutManager getLayoutManager() {
        QuickGridLayoutManager layoutManager = new QuickGridLayoutManager(getContext(), getSpanCount());
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        return layoutManager;
    }

    public <T, B extends ViewBinding> void register(ItemBinder<T, B> binder) {
        mAdapter.addBinder(binder);
    }

    public int getSpanCount() {
        return 2;
    }

    public RecyclerView.ItemDecoration getItemDecoration(RecyclerView.Adapter<?> adapter) {
        return null;
    }

    public abstract SmartRefreshLayout getRefreshLayout();

    public abstract RecyclerView getRecyclerView();

    @CallSuper
    @Override
    public void init() {
        super.init();
        adapterHelper = new QuickAdapterHelper.Builder(mAdapter)
                .setTrailingLoadStateAdapter(new TrailingLoadStateAdapter.OnTrailingListener() {

                    @Override
                    public void onLoad() {
                        if (viewModel.loadMoreEnable()) {
                            viewModel.onLoadMore();
                        }
                    }

                    @Override
                    public void onFailRetry() {
                        if (viewModel.loadMoreEnable()) {
                            viewModel.onLoadMore();
                        }
                    }
                })
                .build();
        getRecyclerView().setLayoutManager(getLayoutManager());
        getRecyclerView().setAdapter(mAdapter);
        if (getRecyclerView().getItemDecorationCount() < 1 && getItemDecoration(mAdapter) != null) {
            getRecyclerView().addItemDecoration(getItemDecoration(mAdapter));
        }
        getRefreshLayout().setEnableLoadMore(false);
        getRefreshLayout().setEnableRefresh(viewModel.refreshEnable());
        if (viewModel.refreshEnable()) {
            getRefreshLayout().setOnRefreshListener(refreshLayout -> viewModel.onRefresh());
        }
        //列表状态观察
        viewModel.getListState().observe(this, state -> {
            switch (state) {
                case ERROR:
                    getRefreshLayout().finishRefresh();
                    adapterHelper.setTrailingLoadState(new LoadState.Error(new Throwable("加载失败")));
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
                    adapterHelper.setTrailingLoadState(new LoadState.NotLoading(true));
                    break;
                case MORE_LOAD_FINISH:
                    getRefreshLayout().finishRefresh();
                    adapterHelper.setTrailingLoadState(new LoadState.NotLoading(true));
                    break;
            }
        });
        viewModel.getItems().observe(this, data -> {
            setList(data.getItems());
            if (ListUtils.isEmpty(data.getItems())) {
                mAdapter.setStateView(getEmptyView());
            }
        });
    }

    public void setList(List<Object> items) {
        mAdapter.setItems(items);
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