package com.github.lany192.widget;


import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 不滚动RecyclerView
 */
public class NoScrollRecyclerView extends RecyclerView {

    public NoScrollRecyclerView(Context context) {
        super(context);
    }

    public NoScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

