package com.github.lany192.box.sample.data.binder;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.box.sample.data.bean.Category;
import com.github.lany192.box.sample.databinding.ItemCategoryBinding;
import com.github.lany192.utils.ImageUtils;

public class CategoryBinder extends ItemBinder<Category, ItemCategoryBinding> {

    @Override
    public int getSpanCount() {
        return 1;
    }

    @Override
    public void convert(@NonNull ItemCategoryBinding binding, @NonNull BaseViewHolder holder, Category item) {
        ImageUtils.show(binding.image, item.getUrl());
        binding.title.setText(item.getName());
    }
}
