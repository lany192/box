package com.github.lany192.box.sample.data.binder;

import androidx.annotation.NonNull;

import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.box.sample.databinding.ItemImageBinding;
import com.github.lany192.utils.ImageUtils;

public class ImageBinder extends ItemBinder<String, ItemImageBinding> {

    @Override
    public void bind(@NonNull ItemImageBinding binding, String url, int position) {
        ImageUtils.show(binding.image, url);
    }
}
