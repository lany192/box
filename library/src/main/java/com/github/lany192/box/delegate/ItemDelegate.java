package com.github.lany192.box.delegate;

import android.content.Context;

import androidx.annotation.NonNull;

import com.github.lany192.box.adapter.ItemViewHolder;

import butterknife.ButterKnife;

/**
 * 多布局代理基类，适用于MultiAdapter适配器
 * @author Administrator
 */
public abstract class ItemDelegate<T> implements Delegate {
    private Context mContext;
    private final T t;
    private int spanSize = 2;

    public ItemDelegate(@NonNull final T item) {
        this.t = item;
    }

    public ItemDelegate(int spanSize, @NonNull final T item) {
        this.t = item;
        this.spanSize = spanSize;
    }

    public T getItem() {
        return t;
    }

    @Override
    public int getSpanSize() {
        return spanSize;
    }

    /**
     * 根据类class的名称生成对应的唯一id
     *
     * @return item类型
     */
    @Override
    public int getItemType() {
        return getLayoutId();
    }

    /**
     * 控价和数据绑定
     */
    public abstract void bind(ItemViewHolder holder, T item, int position);

    @Override
    public void convert(ItemViewHolder holder, Context context) {
        this.mContext = context;
        int position = holder.getAdapterPosition();
        ButterKnife.bind(this, holder.itemView);
        holder.itemView.setOnClickListener(v -> onItemClicked(t, position));
        bind(holder, t, position);
    }

    public void onItemClicked(T item, int position) {
        //item 点击事件
    }

    protected Context getContext() {
        return mContext;
    }
}
