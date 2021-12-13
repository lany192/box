package com.github.lany192.box.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lany192.box.R;
import com.github.lany192.box.databinding.FragmentPageBinding;
import com.github.lany192.box.fragment.BindingFragment;
import com.github.lany192.box.utils.ListUtils;
import com.github.lany192.box.view.EmptyView;
import com.github.lany192.box.view.NetworkView;
import com.github.lany192.utils.NetUtils;
import com.hjq.toast.ToastUtils;

import java.lang.reflect.ParameterizedType;

public abstract class PageListFragment<VM extends PageListViewModel>
        extends BindingFragment<FragmentPageBinding> {
    private final PageListAdapter adapter = new PageListAdapter();
    protected VM viewModel;

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getSpanCount());
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        return layoutManager;
    }

    public void register(ItemBinder binder) {
        adapter.addItemBinder(binder.getTargetClass(), binder);
    }

    public int getSpanCount() {
        return 2;
    }

    public int getItemSpanSize(int viewType, int position) {
        return getSpanCount();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel((Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

        adapter.setGridSpanSizeLookup((gridLayoutManager, viewType, position) -> getItemSpanSize(viewType, position));

        binding.recyclerView.setLayoutManager(getLayoutManager());
        binding.recyclerView.setAdapter(adapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            if (NetUtils.isAvailable(requireContext())) {
                viewModel.onRefresh();
            } else {
                binding.refreshLayout.finishRefresh();
                showNetView();
            }
        });
        adapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            if (NetUtils.isAvailable(requireContext())) {
                viewModel.onLoadMore();
            } else {
                ToastUtils.show("网络异常");
                adapter.getLoadMoreModule().loadMoreComplete();
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
                adapter.getLoadMoreModule().loadMoreComplete();
            }
        });
        viewModel.getItems().observe(this, data -> {
            if (data.isRefresh()) {
                if (ListUtils.isEmpty(data.getItems())) {
                    showEmptyView();
                } else {
                    adapter.setNewInstance(data.getItems());
                }
            } else {
                adapter.addData(data.getItems());
            }
        });

        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                adapter.setEmptyView(R.layout.view_loading);
            }
        });
        return root;
    }

    private void showNetView(){
        NetworkView emptyView = new NetworkView(requireContext());
        emptyView.setMessage("当前网络异常");
        emptyView.setHint("重新点击试试");
        emptyView.setOnRetryListener(() -> viewModel.onLazyLoad());
        adapter.setEmptyView(emptyView);
    }

    private void showEmptyView(){
        EmptyView emptyView = new EmptyView(requireContext());
        emptyView.setMessage("没有发现数据");
        emptyView.setHint("重新点击试试");
        emptyView.setOnRetryListener(() -> viewModel.onLazyLoad());
        adapter.setEmptyView(emptyView);
    }
}