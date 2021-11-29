package com.github.lany192.box.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewBindingAdapter<T extends Object, VB extends ViewBinding> extends BaseQuickAdapter<T, BaseViewHolder> {
    private VB binding;

    public ViewBindingAdapter() {
        super(0, new ArrayList<T>());
    }

    public ViewBindingAdapter(List<T> data) {
        super(0, data);
    }

    private ViewBindingAdapter(@LayoutRes int layoutResId) {
        super(0);
    }

    private ViewBindingAdapter(int layoutResId, @Nullable List<T> data) {
        super(0, data);
    }

    protected abstract VB getBinding(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    @NotNull
    @Override
    protected BaseViewHolder onCreateDefViewHolder(@NotNull ViewGroup parent, int viewType) {
        binding = getBinding(LayoutInflater.from(parent.getContext()), parent);
        return new BaseViewHolder(binding.getRoot());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, T t) {
        bind(binding, t, viewHolder.getBindingAdapterPosition());
    }

    protected abstract void bind(VB binding, T t, int position);
}