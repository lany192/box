package com.github.lany192.widget;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 适配StaggeredGridLayoutManager
 */
public class StaggeredGridRecyclerView extends RecyclerView {
    private Parcelable savedState;

    public StaggeredGridRecyclerView(@NonNull Context context) {
        super(context);
    }

    public StaggeredGridRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StaggeredGridRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            savedState = layoutManager.onSaveInstanceState();
        }
        super.onDetachedFromWindow();
    }

    /**
     * 回复状态，如果状态存在
     */
    public void restoreSaveState() {
        if (savedState != null) {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManager.onRestoreInstanceState(savedState);
            }
        }
    }
}
