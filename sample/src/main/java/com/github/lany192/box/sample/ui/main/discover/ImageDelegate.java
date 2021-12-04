package com.github.lany192.box.sample.ui.main.discover;

import android.view.LayoutInflater;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.box.sample.databinding.ItemImageBinding;
import com.github.lany192.multitype.delegate.ItemDelegate;
import com.github.lany192.utils.ImageUtils;

public class ImageDelegate extends ItemDelegate<String, ItemImageBinding> {

    public ImageDelegate(String data) {
        super(data);
    }

    @Override
    public int getSpanSize() {
        return 1;
    }

    @Override
    public ItemImageBinding getViewBinding() {
        return ItemImageBinding.inflate(LayoutInflater.from(getContext()));
    }

    @Override
    public void onBindItem(ItemImageBinding binding, BaseViewHolder holder, String url, int position) {
        ImageUtils.show(binding.myImageView, url);
    }
}
