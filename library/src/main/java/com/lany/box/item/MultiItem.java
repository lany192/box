package com.lany.box.item;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lany.box.adapter.ItemViewHolder;
import com.lany.box.helper.ViewTypeHelper;

public abstract class MultiItem implements MultiItemEntity {
    protected final String TAG = this.getClass().getSimpleName();
    private int spanSize = 2;
    protected Context mContext;
    protected ItemViewHolder helper;

    @Override
    public int getItemType() {
        return ViewTypeHelper.getInstance().getViewType(this);
    }

    public int getSpanSize() {
        return spanSize;
    }

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void init();

    public void convert(ItemViewHolder helper, Context context) {
        this.mContext = context;
        this.helper = helper;
        init();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        return (T) helper.getView(viewId);
    }

    public MultiItem setSpanSize(int spanSize) {
        this.spanSize = spanSize;
        return this;
    }
}
