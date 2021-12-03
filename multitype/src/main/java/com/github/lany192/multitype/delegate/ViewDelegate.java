package com.github.lany192.multitype.delegate;

import android.content.Context;

import com.github.lany192.multitype.adapter.ItemViewHolder;

public interface ViewDelegate {

    int getItemType();

    /**
     * 站位的大小,默认是2。如需修改复现该方法
     *
     * @return 大小
     */
    default int getSpanSize() {
        return 2;
    }

    void convert(ItemViewHolder holder, Context context);
}
