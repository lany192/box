package com.github.lany192.box.sample.ui.main.index.article;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.lany192.box.fragment.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentItemsBinding;
import com.github.lany192.multitype.adapter.MultiAdapter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public abstract class ItemsFragment<VM extends ItemsViewModel>
        extends BindingFragment<FragmentItemsBinding> {
    protected VM viewModel;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel((Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        MultiAdapter adapter = new MultiAdapter(new ArrayList<>());
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