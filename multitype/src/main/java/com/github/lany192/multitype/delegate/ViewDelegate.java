package com.github.lany192.multitype.delegate;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;


public interface ViewDelegate {

    int getItemType();

    /**
     * 站位的大小,默认是2。如需修改复现该方法
     *
     * @return 大小
     */
    int getSpanSize();

    View getView();

    void onBindView(Context context, BaseViewHolder holder, int position);
}
