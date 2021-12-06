package com.github.lany192.box.items;

import androidx.recyclerview.widget.RecyclerView;

import com.drakeet.multitype.ItemViewBinder;

import java.lang.reflect.ParameterizedType;

public abstract class ItemDelegate<T, VH extends RecyclerView.ViewHolder> extends ItemViewBinder<T, VH> {

    @SuppressWarnings("unchecked")
    public Class<T> getTargetClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public int getSpanCount() {
        return 2;
    }
}
