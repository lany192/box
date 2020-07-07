package com.github.lany192.box.sample.delegate;

import com.github.lany192.box.adapter.ItemViewHolder;
import com.github.lany192.box.delegate.ItemDelegate;
import com.github.lany192.box.sample.R;

public class HelloDelegate extends ItemDelegate<String> {

    public HelloDelegate(String data) {
        super(data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_hello;
    }

    @Override
    public void bind(ItemViewHolder holder, String pic, int position) {
        holder.setImageFullWidth(R.id.my_image_view, pic);
    }
}
