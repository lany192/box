package com.github.lany192.box.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseBinderAdapter;
import com.github.lany192.box.databinding.FragmentItemsBinding;
import com.github.lany192.box.fragment.BindingFragment;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

public abstract class ItemsFragment<VM extends ItemsViewModel>
        extends BindingFragment<FragmentItemsBinding> {
    protected VM viewModel;
    private final BaseBinderAdapter adapter = new BaseBinderAdapter();
    private final HashMap<Class<?>, Integer> spanSizeMap = new HashMap<>();

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getSpanCount());
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        return layoutManager;
    }

    public <D extends ItemDelegate> void register(D delegate) {
        spanSizeMap.put(delegate.getTargetClass(), delegate.getSpanCount());
        adapter.addItemBinder(delegate.getTargetClass(), delegate);
    }

    public int getSpanCount() {
        return 2;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel((Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return spanSizeMap.get(adapter.getItem(position).getClass());
                }
            });
        }
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.onLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.onRefresh();
            }
        });
        viewModel.getRefreshState().observe(this, refreshing -> {
            if (!refreshing) {
                binding.refreshLayout.finishRefresh();
            }
        });
        viewModel.getLoadMoreState().observe(this, moreLoading -> {
            if (!moreLoading) {
                binding.refreshLayout.finishLoadMore();
            }
        });
        viewModel.getItems().observe(this, data -> {
            if (data.isRefresh()) {
                adapter.setNewInstance(data.getItems());
            } else {
                adapter.addData(data.getItems());
            }
        });

//        viewModel.getLoading().observe(this, loading -> {
//            if (loading) {
//                showLoading();
//            } else {
//                showContent();
//            }
//        });
        return root;
    }


}