package com.github.lany192.box.sample.ui.main.index.girl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.lany192.box.items.ItemDelegate;
import com.github.lany192.box.sample.databinding.ItemGirlBinding;
import com.github.lany192.utils.ImageUtils;

public class GirlDelegate extends ItemDelegate<String, ItemGirlBinding> {

    @Override
    public ItemGirlBinding getViewBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemGirlBinding.inflate(inflater, parent, false);
    }

    @Override
    public void onBind(ItemGirlBinding binding, String url, int position) {
        ImageUtils.show(binding.image, url);
    }
}
