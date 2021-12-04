package com.github.lany192.box.sample.ui.main.discover;

import android.view.LayoutInflater;

import com.github.lany192.box.sample.databinding.ItemImageBinding;
import com.github.lany192.multitype.delegate.ItemDelegate;
import com.github.lany192.utils.ImageUtils;

public class ImageDelegate extends ItemDelegate<String, ItemImageBinding> {

    public ImageDelegate(String data) {
        super(data);
    }

    @Override
    public ItemImageBinding getViewBinding(LayoutInflater inflater) {
        return ItemImageBinding.inflate(inflater);
    }

    @Override
    public void onBind(ItemImageBinding binding, String url, int position) {
        ImageUtils.show(binding.image, url);
    }

    @Override
    public int getSpanSize() {
        return 1;
    }
}
