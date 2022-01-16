package com.github.lany192.box.sample.data.binder;

import androidx.annotation.NonNull;

import com.github.lany192.arch.items.BindingItemBinder;
import com.github.lany192.box.sample.data.bean.Category;
import com.github.lany192.box.sample.databinding.ItemCategoryBinding;
import com.github.lany192.utils.ImageUtils;

public class CategoryBinder extends BindingItemBinder<Category, ItemCategoryBinding> {

    @Override
    public void convert(@NonNull BindingHolder<ItemCategoryBinding> holder, Category item) {
        ImageUtils.show(holder.getBinding().image, item.getUrl());
        holder.getBinding().title.setText(item.getName());
    }
}
