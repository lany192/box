package com.github.lany192.box.items;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.github.lany192.adapter.ItemViewBinder;

import java.lang.reflect.ParameterizedType;

public abstract class ItemDelegate<T, VB extends ViewBinding> extends ItemViewBinder<T, BaseViewHolder> {
    private VB binding;

    public Class<T> getTargetClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public int getSpanCount() {
        return 2;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup parent) {
        binding = getViewBinding(layoutInflater, parent);
        return new BaseViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, T item) {
        holder.itemView.setOnClickListener(v -> onItemClicked(item, holder.getBindingAdapterPosition()));
        onBind(binding, item, holder.getBindingAdapterPosition());
    }

    public abstract VB getViewBinding(LayoutInflater inflater, ViewGroup parent);

    public abstract void onBind(VB binding, T t, int position);

    public void onItemClicked(T item, int position){

    }
}