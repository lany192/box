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

public abstract class PageFragment<VM extends PageViewModel>
        extends BindingFragment<FragmentPageBinding> {
    protected VM viewModel;
    private final PageAdapter adapter = new PageAdapter();

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