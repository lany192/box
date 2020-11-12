package com.github.lany192.box.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.github.lany192.box.R;
import com.github.lany192.box.interfaces.OnRefreshMoreListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * RecyclerView的下拉刷新和上拉加载及置顶功能封装
 */
public class ShowView extends FrameLayout {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ImageView mGotoTopBtn;

    private int gotoTopCount = 5;
    private BaseQuickAdapter<?, ?> mAdapter;
    private OnRefreshMoreListener mListener;

    public ShowView(Context context) {
        super(context);
        init();
    }

    public ShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.show_view, this, true);
        mRefreshLayout = view.findViewById(R.id.show_view_refresh_layout);
        mRecyclerView = view.findViewById(R.id.show_view_recycler_view);
        mGotoTopBtn = view.findViewById(R.id.show_view_goto_top_img);
        mGotoTopBtn.setOnClickListener(v -> gotoTop());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager || mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                    mGotoTopBtn.setVisibility(firstVisibleItem > gotoTopCount ? View.VISIBLE : View.GONE);
                } else {
                    mGotoTopBtn.setVisibility(View.GONE);
                }
            }
        });
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mAdapter.getLoadMoreModule().setEnableLoadMore(false);
            if (mListener != null) {
                mListener.onRefresh();
            }
            mRefreshLayout.finishRefresh();
            mAdapter.getLoadMoreModule().setEnableLoadMore(true);
        });
        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 10);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRecyclerView.setLayoutManager(layout);
    }

    public void setRefreshEnabled(boolean enabled) {
        mRefreshLayout.setEnabled(enabled);
    }

    public void setEnableLoadMore(boolean enable) {
        if (mAdapter != null) {
            mAdapter.getLoadMoreModule().setEnableLoadMore(enable);
        }
    }

    public void setAdapter(BaseQuickAdapter<?, ?> adapter) {
        if (adapter instanceof LoadMoreModule) {
            this.mAdapter = adapter;
            mAdapter.getLoadMoreModule().setAutoLoadMore(true);
            mAdapter.getLoadMoreModule().setEnableLoadMore(true);
            mAdapter.getLoadMoreModule().setLoadMoreView(new FooterView());
            mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
                if (mListener != null) {
                    mListener.onLoadMore();
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        } else {
            throw new IllegalArgumentException("Please first implements LoadMoreModule");
        }
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mRecyclerView.addOnScrollListener(listener);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }

    public void smoothScrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    public void setGotoTopCount(int count) {
        this.gotoTopCount = count;
    }

    public void setOnRefreshMoreListener(OnRefreshMoreListener listener) {
        this.mListener = listener;
    }

    public void finishRefresh() {
        mRefreshLayout.finishRefresh();
    }

    public void stop() {
        mRefreshLayout.finishRefresh();
        if (mAdapter != null) {
            mAdapter.getLoadMoreModule().loadMoreComplete();
        }
    }

    /**
     * 显示没有更多
     */
    public void end() {
        mRefreshLayout.finishRefresh();
        if (mAdapter != null) {
            mAdapter.getLoadMoreModule().loadMoreComplete();
            mAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    public void loadMoreFail() {
        mRefreshLayout.finishRefresh();
        if (mAdapter != null) {
            mAdapter.getLoadMoreModule().loadMoreComplete();
            mAdapter.getLoadMoreModule().loadMoreFail();
        }
    }

    public void gotoTop() {
        scrollToPosition(0);
        mGotoTopBtn.setVisibility(View.GONE);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        mRecyclerView.setHasFixedSize(hasFixedSize);
    }
}
