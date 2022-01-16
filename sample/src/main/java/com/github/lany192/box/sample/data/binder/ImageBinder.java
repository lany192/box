package com.github.lany192.box.sample.data.binder;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.arch.items.BindingItemBinder;
import com.github.lany192.box.sample.databinding.ItemImageBinding;
import com.github.lany192.utils.ImageUtils;

public class ImageBinder extends BindingItemBinder<String, ItemImageBinding> {

    @Override
    public void convert(@NonNull BaseViewHolder holder, String url) {
        ImageUtils.show(binding.image, url);
    }
}
