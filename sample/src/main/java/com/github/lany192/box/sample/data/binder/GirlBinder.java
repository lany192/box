package com.github.lany192.box.sample.data.binder;

import androidx.annotation.NonNull;

import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.box.sample.databinding.ItemGirlBinding;
import com.github.lany192.utils.ImageUtils;

public class GirlBinder extends ItemBinder<String, ItemGirlBinding> {

    @Override
    public void convert(@NonNull ItemGirlBinding binding, String url, int position) {
        ImageUtils.show(binding.image, url);
    }
}
