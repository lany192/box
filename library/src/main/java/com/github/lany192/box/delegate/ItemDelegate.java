package com.github.lany192.box.delegate;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.github.lany192.box.adapter.ItemViewHolder;
import com.github.lany192.box.helper.ItemTypeHelper;

import butterknife.ButterKnife;

/**
 * 多布局代理基类，适用于MultiAdapter适配器
 */
public abstract class ItemDelegate<T> implements MultiItemEntity {
    private Context mContext;
    private T mData;

    public ItemDelegate(@NonNull final T data) {
        this.mData = data;
    }

    public T getData() {
        return mData;
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
    public abstract void init(ItemViewHolder holder, T data, int position);

    public void convert(ItemViewHolder holder, Context context) {
        this.mContext = context;
        int position = holder.getAdapterPosition();
        ButterKnife.bind(this, holder.itemView);
        holder.itemView.setOnClickListener(v -> onItemClicked(mData, position));
        init(holder, mData, position);
    }

    public void onItemClicked(T data, int position) {
        //item 点击事件
    }

    protected Context getContext() {
        return mContext;
    }
}
