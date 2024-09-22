package com.lany192.box.demo.binder;

import androidx.annotation.NonNull;

import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.utils.ImageUtils;
import com.lany192.box.network.data.bean.Category;
import com.lany192.box.demo.databinding.ItemCategoryBinding;

public class CategoryBinder extends ItemBinder<Category, ItemCategoryBinding> {

    @Override
    public void convert(@NonNull ItemCategoryBinding binding, Category item, int position) {
        ImageUtils.show(binding.image, item.getUrl());
        binding.title.setText(item.getName());
    }
}
