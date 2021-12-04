package com.github.lany192.box.sample.ui.main.index.girl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.lany192.box.sample.databinding.ItemGirlBinding;
import com.github.lany192.multitype.delegate.ItemDelegate;
import com.github.lany192.utils.ImageUtils;

public class GirlDelegate extends ItemDelegate<String, ItemGirlBinding> {

    public GirlDelegate(String data) {
        super(data);
    }

    @Override
    public ItemGirlBinding getViewBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemGirlBinding.inflate(inflater, parent, false);
    }

    @Override
    public void onBind(ItemGirlBinding binding, String url, int position) {
        ImageUtils.show(binding.image, url);
    }

    @Override
    public int getSpanSize() {
        return 1;
    }
}
