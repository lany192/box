package com.github.lany192.multitype.delegate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * 多布局代理基类，适用于MultiAdapter适配器
 */
public abstract class ItemDelegate<T, VB extends ViewBinding> implements ViewDelegate {
    private final T t;
    private Context context;
    private VB binding;

    public ItemDelegate(@NonNull final T t) {
        this.t = t;
    }

    public T getItem() {
        return t;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 根据类class的名称生成对应的唯一id
     */
    @Override
    public int getItemType() {
        return getClass().getName().hashCode();
    }

    @Override
    public View getView(Context context, ViewGroup parent) {
        this.context = context;
        this.binding = getViewBinding(LayoutInflater.from(context), parent);
        return binding.getRoot();
    }

    @Override
    public int getSpanSize() {
        return 1;
    }

    public abstract VB getViewBinding(LayoutInflater inflater, ViewGroup parent);

    public abstract void onBind(VB binding, T t, int position);

    @Override
    public void onBind(BaseViewHolder holder, int position) {
        onBind(binding, t, position);
        holder.itemView.setOnClickListener(v -> onItemClicked(t, position));
    }

    public void onItemClicked(T item, int position) {
        //item 点击事件
    }
}
