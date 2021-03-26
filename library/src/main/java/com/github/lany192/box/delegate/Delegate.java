package com.github.lany192.box.delegate;

import android.content.Context;

import androidx.annotation.LayoutRes;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.github.lany192.box.adapter.ItemViewHolder;

/**
 * @author Administrator
 */
public interface Delegate extends MultiItemEntity {
    /**
     * 返回布局文件id
     *
     * @return 布局文件id
     */
    @LayoutRes
    int getLayoutId();

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
