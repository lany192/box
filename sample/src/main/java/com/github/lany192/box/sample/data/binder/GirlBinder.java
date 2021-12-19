package com.github.lany192.box.sample.data.binder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.box.sample.databinding.ItemGirlBinding;
import com.github.lany192.utils.ImageUtils;

public class GirlBinder extends ItemBinder<String, ItemGirlBinding> {

    @Override
    public void bind(ItemGirlBinding binding, String url, int position) {
        ImageUtils.show(binding.image, url);
    }

    @NonNull
    @Override
    public ItemGirlBinding onCreateViewBinding(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int i) {
        return ItemGirlBinding.inflate(inflater, parent, false);
    }
}
