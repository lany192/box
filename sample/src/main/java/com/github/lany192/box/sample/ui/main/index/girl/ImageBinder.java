package com.github.lany192.box.sample.ui.main.index.girl;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.binder.QuickItemBinder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.box.sample.R;
import com.github.lany192.utils.ImageUtils;

public class ImageBinder extends QuickItemBinder<String> {

    @Override
    public void convert(@NonNull BaseViewHolder holder, String url) {
        ImageUtils.show(holder.getView(R.id.image), url);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_image;
    }
}
