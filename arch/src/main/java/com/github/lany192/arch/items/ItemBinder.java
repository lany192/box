package com.github.lany192.arch.items;

import androidx.viewbinding.ViewBinding;

import java.lang.reflect.ParameterizedType;

/**
 * 多类型Binder
 */
public abstract class ItemBinder<T, VB extends ViewBinding> extends BindingItemBinder<T, VB> {

    public Class<T> getTargetClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public int getSpanCount() {
        return 2;
    }

}
