package com.github.lany192.box.sample.data.binder;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.arch.items.BindingItemBinder;
import com.github.lany192.box.sample.data.bean.Category;
import com.github.lany192.box.sample.databinding.ItemCategoryBinding;
import com.github.lany192.utils.ImageUtils;

public class CategoryBinder extends BindingItemBinder<Category, ItemCategoryBinding> {

    @Override
    public void convert(@NonNull BaseViewHolder holder, Category item) {
        ImageUtils.show(binding.image, item.getUrl());
        binding.title.setText(item.getName());
    }
}
