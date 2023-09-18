package com.github.lany192.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 垂直滚动TextView,上下跑马灯
 */
public class MarqueeTextView extends MarqueeLayout {
    private OnScrollListener listener;

    public MarqueeTextView(Context context) {
        this(context, null);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListener(OnScrollListener listener) {
        this.listener = listener;
    }

    /**
     * 设置显示的内容
     *
     * @param items 内容列表
     */
    public void setItems(List<CharSequence> items, long duration) {
        if (items == null || items.size() == 0) {
            return;
        }
        removeAllViews();
        List<TextView> views = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            TextView view = new TextView(getContext());
            view.setText(items.get(i));
            int finalI = i;
            view.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItem(finalI);
                }
            });
            views.add(view);
        }
        setViews(views, duration);
    }

    public interface OnScrollListener {
        void onItem(int index);
    }
}