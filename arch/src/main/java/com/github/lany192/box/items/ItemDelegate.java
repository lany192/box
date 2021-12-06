package com.github.lany192.box.items;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.drakeet.multitype.ItemViewBinder;

import java.lang.reflect.ParameterizedType;

public abstract class ItemDelegate<T, VB extends ViewBinding> extends ItemViewBinder<T, BaseViewHolder> {

    public Class<T> getTargetClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public int getSpanCount() {
        return 2;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup parent) {
        VB binding = getViewBinding(layoutInflater, parent);
        return new BaseViewHolder(binding.getRoot());
    }

    public abstract VB getViewBinding(LayoutInflater inflater, ViewGroup parent);
}
