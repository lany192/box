package com.github.lany192.box.sample.data.binder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.box.sample.databinding.ItemGirlBinding;
import com.github.lany192.utils.ImageUtils;

public class GirlBinder extends ItemBinder<String, ItemGirlBinding> {

    @NonNull
    @Override
    public ItemGirlBinding onCreateViewBinding(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int i) {
        return ItemGirlBinding.inflate(inflater, parent, false);
    }

    @Override
    public void convert(@NonNull ItemGirlBinding binding, @NonNull BaseViewHolder holder, String url) {
        ImageUtils.show(binding.image, url);
    }
}
