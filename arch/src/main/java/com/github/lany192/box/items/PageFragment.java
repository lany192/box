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

import java.lang.reflect.ParameterizedType;

public abstract class PageFragment<VM extends PageViewModel>
        extends BindingFragment<FragmentPageBinding> {
    private final PageAdapter adapter = new PageAdapter();
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
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> viewModel.onRefresh());

        adapter.getLoadMoreModule().setOnLoadMoreListener(() -> viewModel.onLoadMore());

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
                    EmptyView emptyView = new EmptyView(requireContext());
                    emptyView.setMessage("没有发现数据");
                    emptyView.setHint("重新点击试试");
                    emptyView.setOnRetryListener(() -> viewModel.onLazyLoad());
                    adapter.setEmptyView(emptyView);
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


}