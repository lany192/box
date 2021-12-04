package com.github.lany192.multitype.delegate;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;


public interface ViewDelegate {

    int getItemType();

    int getSpanSize();

    View getView(Context context, ViewGroup parent);

    void onBind(BaseViewHolder holder, int position);
}
