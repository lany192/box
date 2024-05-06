package com.github.lany192.layoutmanager;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class ScrollLayoutManager extends LinearLayoutManager {
    private boolean scrollEnabled = true;

    public ScrollLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean enabled) {
        this.scrollEnabled = enabled;
    }

    @Override
    public boolean canScrollVertically() {
        return scrollEnabled && super.canScrollVertically();
    }

    @Override
    public boolean canScrollHorizontally() {
        return scrollEnabled && super.canScrollHorizontally();
    }
}