package com.github.lany192.arch.items;

import android.content.Context;

import androidx.annotation.LayoutRes;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

public abstract class ItemDelegate<T> implements MultiItemEntity {
    protected final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    protected Logger.Builder log = XLog.tag(TAG);
    protected final T mData;

    public ItemDelegate(final T data) {
        this.mData = data;
    }

    /**
     * 根据类class的名称生成对应的唯一id
     *
     * @return item类型
     */
    @Override
    public int getItemType() {
        return ItemTypeHelper.getInstance().getViewType(this);
    }

    /**
     * 站位的大小,默认是2。如需修改复现该方法
     *
     * @return 大小
     */
    public int getSpanSize() {
        return 2;
    }

    /**
     * 返回布局文件id
     *
     * @return 布局文件id
     */
    @LayoutRes
    public abstract int getLayoutId();

    /**
     * 初始化方法
     */
    public abstract void init();

    public void convert(BaseViewHolder helper, Context context) {
        this.mContext = context;
        init();
    }

    public Context getContext() {
        return mContext;
    }

    public T getData() {
        return mData;
    }
}
