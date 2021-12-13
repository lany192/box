package com.github.lany192.box.items;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder;

import java.lang.reflect.ParameterizedType;

public abstract class ItemBinder<T, VB extends ViewBinding> extends QuickViewBindingItemBinder<T, VB> {

    public Class<T> getTargetClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public int getSpanCount() {
        return 2;
    }

    @Override
    public void convert(@NonNull BinderVBHolder<VB> holder, T t) {
        bind(holder.getViewBinding(), t, holder.getBindingAdapterPosition());
    }

    public abstract void bind(VB binding, T t, int position);
}
