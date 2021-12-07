package com.github.lany192.box.items;

import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder;

import java.lang.reflect.ParameterizedType;

public abstract class ItemDelegate<T, VB extends ViewBinding> extends QuickViewBindingItemBinder<T, VB> {

    public Class<T> getTargetClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public int getSpanCount() {
        return 2;
    }
}
