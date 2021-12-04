package com.github.lany192.multitype.delegate;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * 多布局代理基类，适用于MultiAdapter适配器
 */
public abstract class ItemDelegate<T, VB extends ViewBinding> implements ViewDelegate {
    private final T t;
    private Context context;

    public ItemDelegate(@NonNull final T t) {
        this.t = t;
    }

    public T getItem() {
        return t;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 根据类class的名称生成对应的唯一id
     */
    @Override
    public int getItemType() {
        return getClass().getName().hashCode();
    }

    @Override
    public View getView() {
        return getViewBinding().getRoot();
    }

    public abstract VB getViewBinding();

    public abstract void onBindItem(VB binding, BaseViewHolder holder, T t, int position);

    @Override
    public void onBindView(Context context, BaseViewHolder holder, int position) {
        this.context = context;
        onBindItem(getViewBinding(), holder, t, position);
        holder.itemView.setOnClickListener(v -> onItemClicked(t, position));
    }

    public void onItemClicked(T item, int position) {
        //item 点击事件
    }
}
