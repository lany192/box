package com.github.lany192.box.sample.ui.main.index.girl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder;
import com.github.lany192.box.sample.databinding.ItemGirlBinding;
import com.github.lany192.utils.ImageUtils;

public class GirlDelegate extends QuickViewBindingItemBinder<String, ItemGirlBinding> {

    @Override
    public void convert(@NonNull BinderVBHolder<ItemGirlBinding> holder, String url) {
        ImageUtils.show(holder.getViewBinding().image, url);
    }

    @NonNull
    @Override
    public ItemGirlBinding onCreateViewBinding(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int i) {
        return ItemGirlBinding.inflate(inflater, parent, false);
    }
}
