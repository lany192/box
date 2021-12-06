package com.github.lany192.box.items;

import com.drakeet.multitype.ItemViewBinder;

import java.lang.reflect.ParameterizedType;

public abstract class ItemDelegate<T> extends ItemViewBinder<T, com.github.lany192.box.items.BaseViewHolder> {

    @SuppressWarnings("unchecked")
    public Class<T> getTargetClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public int getSpanCount() {
        return 2;
    }
}
