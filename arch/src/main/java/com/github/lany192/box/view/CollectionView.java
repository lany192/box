package com.github.lany192.box.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseBinderAdapter;
import com.github.lany192.box.R;
import com.github.lany192.box.interfaces.OnRefreshMoreListener;
import com.github.lany192.layout.FixDragFrameLayout;
import com.github.lany192.widget.StaggeredGridRecyclerView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * RecyclerView的下拉刷新和上拉加载及置顶功能封装
 */
public class CollectionView extends FixDragFrameLayout {
    private final SmartRefreshLayout refreshLayout;
    private final StaggeredGridRecyclerView recyclerView;
    private final ImageView gotoTopBtn;

    private int gotoTopCount = 5;
    private OnRefreshMoreListener mListener;

    public CollectionView(Context context) {
        this(context, null, 0);
    }

    public CollectionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollectionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.items_layout, this, true);
        refreshLayout = view.findViewById(R.id.show_view_refresh_layout);
        recyclerView = view.findViewById(R.id.show_view_recycler_view);
        gotoTopBtn = view.findViewById(R.id.show_view_goto_top_img);
        gotoTopBtn.setOnClickListener(v -> gotoTop());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    int firstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    gotoTopBtn.setVisibility(firstVisibleItem > gotoTopCount ? View.VISIBLE : View.GONE);
                } else {
                    gotoTopBtn.setVisibility(View.GONE);
                }
                if (layoutManager instanceof StaggeredGridLayoutManager) {
                    //防止第一行到顶部有空白区域
                    ((StaggeredGridLayoutManager) layoutManager).invalidateSpanAssignments();
                }
            }
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            if (mListener != null) {
                mListener.onRefresh();
            }
            this.refreshLayout.finishRefresh();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (mListener != null) {
                mListener.onLoadMore();
            }
            this.refreshLayout.finishLoadMore();
        });
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 10);
    }

    /**
     * 回复状态，如果状态存在
     */
    public void restoreSaveState() {
        recyclerView.restoreSaveState();
    }

    public SmartRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public void setToolbarHeight(int height) {
        recyclerView.setPadding(recyclerView.getLeft(), recyclerView.getTop() + height,
                recyclerView.getRight(), recyclerView.getBottom());
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        recyclerView.setLayoutManager(layout);
    }

    public void setEnableRefresh(boolean enable) {
        refreshLayout.setEnableRefresh(enable);
    }

    public void setEnableLoadMore(boolean enable) {
        refreshLayout.setEnableLoadMore(enable);
    }

    public void setAdapter(BaseBinderAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        recyclerView.addOnScrollListener(listener);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        recyclerView.addItemDecoration(decor);
    }

    public void smoothScrollToPosition(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    public void scrollToPosition(int position) {
        recyclerView.scrollToPosition(position);
    }

    public void setGotoTopCount(int count) {
        this.gotoTopCount = count;
    }

    public void setOnRefreshMoreListener(OnRefreshMoreListener listener) {
        this.mListener = listener;
    }

    public void finishRefresh() {
        refreshLayout.finishRefresh();
    }

    public void stop() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    /**
     * 显示没有更多
     */
    public void end() {
        refreshLayout.finishRefreshWithNoMoreData();
    }

    public void loadMoreFail() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore(false);
    }

    public void gotoTop() {
        scrollToPosition(0);
        gotoTopBtn.setVisibility(View.GONE);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        recyclerView.setHasFixedSize(hasFixedSize);
    }
}
