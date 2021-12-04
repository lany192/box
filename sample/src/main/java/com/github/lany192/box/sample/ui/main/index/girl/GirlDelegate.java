package com.github.lany192.box.sample.ui.main.index.girl;

import android.view.LayoutInflater;

import com.github.lany192.box.sample.databinding.ItemGirlBinding;
import com.github.lany192.multitype.delegate.ItemDelegate;
import com.github.lany192.utils.ImageUtils;

public class GirlDelegate extends ItemDelegate<String, ItemGirlBinding> {

    public GirlDelegate(String data) {
        super(data);
    }

    @Override
    public ItemGirlBinding getViewBinding(LayoutInflater inflater) {
        return ItemGirlBinding.inflate(inflater);
    }

    @Override
    public void onBind(ItemGirlBinding binding, String url, int position) {
        ImageUtils.show(binding.myImageView, url);
    }

    @Override
    public int getSpanSize() {
        return 1;
    }
}
