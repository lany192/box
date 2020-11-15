package com.github.lany192.box.sample.delegate;

import com.github.lany192.box.adapter.ItemViewHolder;
import com.github.lany192.box.delegate.ItemDelegate;
import com.github.lany192.box.sample.R;

public class ImageDelegate extends ItemDelegate<String> {

    public ImageDelegate(String data) {
        super(data);
    }

    @Override
    public int getSpanSize() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_image;
    }

    @Override
    public void bind(ItemViewHolder holder, String pic, int position) {
        holder.setImage(R.id.my_image_view, pic,true);
    }
}
