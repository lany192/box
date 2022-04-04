package com.github.lany192.arch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.lany192.arch.databinding.ViewItemsBinding;
import com.github.lany192.view.BindingView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * RecyclerView的下拉刷新和上拉加载及置顶功能封装
 */
public class ItemsView extends BindingView<ViewItemsBinding> {
    private int gotoTopCount = 5;

    public ItemsView(@NonNull Context context) {
        super(context);
    }

    public ItemsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(@Nullable AttributeSet attrs) {
        binding.gotoTop.setOnClickListener(v -> gotoTop());
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    int firstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    binding.gotoTop.setVisibility(firstVisibleItem > gotoTopCount ? View.VISIBLE : View.GONE);
                } else {
                    binding.gotoTop.setVisibility(View.GONE);
                }
            }
        });
        binding.recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 10);
    }

    public RecyclerView getRecyclerView() {
        return binding.recyclerView;
    }

    public SmartRefreshLayout getRefreshLayout() {
        return binding.refreshLayout;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        binding.recyclerView.setLayoutManager(layout);
    }

    public void setEnableRefresh(boolean enable) {
        binding.refreshLayout.setEnableRefresh(enable);
    }

    public void setEnableLoadMore(boolean enable) {
        binding.refreshLayout.setEnableLoadMore(enable);
    }

    public void setAdapter(BaseQuickAdapter<?, ?> adapter) {
        binding.recyclerView.setAdapter(adapter);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        binding.recyclerView.addOnScrollListener(listener);
    }

    public void setItemAnimator(@Nullable RecyclerView.ItemAnimator animator) {
        binding.recyclerView.setItemAnimator(animator);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        binding.recyclerView.addItemDecoration(decor);
    }

    public void smoothScrollToPosition(int position) {
        binding.recyclerView.smoothScrollToPosition(position);
    }

    public void scrollToPosition(int position) {
        binding.recyclerView.scrollToPosition(position);
    }

    public void setGotoTopCount(int count) {
        this.gotoTopCount = count;
    }

    /**
     * 显示没有更多
     */
    public void end() {
        binding.refreshLayout.finishRefresh();
        binding.refreshLayout.finishLoadMoreWithNoMoreData();
    }

    public void fail() {
        binding.refreshLayout.finishRefresh();
        binding.refreshLayout.finishLoadMore(false);
    }

    public void gotoTop() {
        scrollToPosition(0);
        binding.gotoTop.setVisibility(View.GONE);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        binding.recyclerView.setHasFixedSize(hasFixedSize);
    }

}
