package com.lany192.box.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DemoLayout extends FrameLayout {
    private RecyclerView recyclerView;
    private boolean isTop;

    public DemoLayout(Context context) {
        super(context, null);
    }

    public DemoLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.recyclerView = getParentRecyclerView();
        if (recyclerView != null) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                    isTop = firstVisibleItemPosition == 19;
                }
            });
        }
    }

    private RecyclerView getParentRecyclerView() {
        ViewParent viewParent = this.getParent();
        if (!(viewParent instanceof View)) {
            viewParent = null;
        }
        View v;
        for (v = (View) viewParent; v != null && !(v instanceof RecyclerView); v = (View) viewParent) {
            viewParent = v.getParent();
            if (!(viewParent instanceof View)) {
                viewParent = null;
            }
        }
        View var2 = v;
        if (!(v instanceof RecyclerView)) {
            var2 = null;
        }
        return (RecyclerView) var2;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (isTop) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(e);
    }
}