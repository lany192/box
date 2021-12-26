package com.github.lany192.box.sample.data.binder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

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
    public void bind(ItemCategoryBinding binding, Category item, int position) {
        ImageUtils.show(binding.image, item.getUrl());
        binding.title.setText(item.getName());
    }

    @NonNull
    @Override
    public ItemCategoryBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        return ItemCategoryBinding.inflate(layoutInflater, viewGroup, false);
    }
}
