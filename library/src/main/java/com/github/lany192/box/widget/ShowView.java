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
import com.github.lany192.box.R;
import com.github.lany192.box.interfaces.OnRefreshMoreListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * RecyclerView的下拉刷新和上拉加载及置顶功能封装
 */
public class ShowView extends FrameLayout {
    private final SmartRefreshLayout mRefreshLayout;
    private final RecyclerView mRecyclerView;
    private final ImageView mGotoTopBtn;

    private int gotoTopCount = 5;
    private OnRefreshMoreListener mListener;

    public ShowView(Context context) {
        this(context, null, 0);
    }

    public ShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
            if (mListener != null) {
                mListener.onRefresh();
            }
            mRefreshLayout.finishRefresh();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (mListener != null) {
                mListener.onLoadMore();
            }
            mRefreshLayout.finishLoadMore();
        });
        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 10);
    }

    public SmartRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRecyclerView.setLayoutManager(layout);
    }

    public void setEnableRefresh(boolean enable) {
        mRefreshLayout.setEnableRefresh(enable);
    }

    public void setEnableLoadMore(boolean enable) {
        mRefreshLayout.setEnableLoadMore(enable);
    }

    public void setAdapter(BaseQuickAdapter<?, ?> adapter) {
        mRecyclerView.setAdapter(adapter);
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

    public void finish() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    public void gotoTop() {
        scrollToPosition(0);
        mGotoTopBtn.setVisibility(View.GONE);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        mRecyclerView.setHasFixedSize(hasFixedSize);
    }
}
