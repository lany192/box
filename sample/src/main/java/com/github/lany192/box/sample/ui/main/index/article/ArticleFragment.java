package com.github.lany192.box.sample.ui.main.index.article;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.lany192.box.fragment.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentArticleBinding;
import com.github.lany192.multitype.adapter.MultiAdapter;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArticleFragment extends BindingFragment<FragmentArticleBinding> {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        ArticleViewModel viewModel = getFragmentViewModel(ArticleViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        MultiAdapter adapter = new MultiAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(adapter);
        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ToastUtils.show("加载更多");
                new Handler().postDelayed(refreshLayout::finishLoadMore, 2000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ToastUtils.show("刷新");
                new Handler().postDelayed(refreshLayout::finishRefresh, 2000);
            }
        });


        viewModel.getItems().observe(this, adapter::setNewInstance);
        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                showLoading();
            } else {
                showContent();
            }
        });
        return root;
    }
}