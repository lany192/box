package com.github.lany192.box.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lany192.box.databinding.FragmentPageBinding;
import com.github.lany192.box.fragment.BindingFragment;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

public abstract class PageFragment<VM extends PageViewModel>
        extends BindingFragment<FragmentPageBinding> {
    protected VM viewModel;
    private final PageAdapter adapter = new PageAdapter();
    private final HashMap<Class<?>, Integer> spanSizeMap = new HashMap<>();

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getSpanCount());
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        return layoutManager;
    }

    public <B extends ItemBinder> void register(B delegate) {
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