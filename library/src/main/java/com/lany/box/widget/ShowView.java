package com.lany.box.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lany.box.R;
import com.lany.box.interfaces.OnRefreshMoreListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * RecyclerView的下拉刷新和上拉加载及置顶功能封装
 */
public class ShowView extends FrameLayout implements BaseQuickAdapter.RequestLoadMoreListener {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ImageView mGotoTopBtn;

    private int gotoTopCount = 5;
    private BaseQuickAdapter mAdapter;
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
        mGotoTopBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoTop();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

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
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAdapter.setEnableLoadMore(false);
                if (mListener != null) {
                    mListener.onRefresh();
                }
                mRefreshLayout.finishRefresh();
                mAdapter.setEnableLoadMore(true);
            }
        });
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRecyclerView.setLayoutManager(layout);
    }

    public void setRefreshEnabled(boolean enabled) {
        mRefreshLayout.setEnabled(enabled);
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        this.mAdapter = adapter;
        this.mAdapter.setLoadMoreView(new FooterView());
        mRecyclerView.setAdapter(adapter);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
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
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if (mAdapter != null) {
            mAdapter.loadMoreComplete();
        }
    }

    /**
     * 显示没有更多
     */
    public void end() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if (mAdapter != null) {
            mAdapter.loadMoreComplete();
            mAdapter.loadMoreEnd();
        }
    }

    public void loadMoreFail() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if (mAdapter != null) {
            mAdapter.loadMoreComplete();
            mAdapter.loadMoreFail();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (mListener != null) {
            mListener.onLoadMore();
        }
    }

    public void gotoTop() {
        scrollToPosition(0);
        mGotoTopBtn.setVisibility(View.GONE);
    }
}
