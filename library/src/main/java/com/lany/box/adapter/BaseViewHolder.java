package com.lany.box.adapter;

import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseViewHolder {
    public final View itemView;

    public BaseViewHolder(View itemView) {
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }
}
