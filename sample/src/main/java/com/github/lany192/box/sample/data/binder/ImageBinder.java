package com.github.lany192.box.sample.data.binder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.box.sample.databinding.ItemImageBinding;
import com.github.lany192.utils.ImageUtils;

public class ImageBinder extends ItemBinder<String, ItemImageBinding> {

    @Override
    public void bind(ItemImageBinding binding, String url, int position) {
        ImageUtils.show(binding.image, url);
    }

    @NonNull
    @Override
    public ItemImageBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        return ItemImageBinding.inflate(layoutInflater, viewGroup, false);
    }
}
