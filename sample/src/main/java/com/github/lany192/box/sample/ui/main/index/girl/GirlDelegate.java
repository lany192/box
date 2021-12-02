package com.github.lany192.box.sample.ui.main.index.girl;

import com.github.lany192.box.sample.R;
import com.github.lany192.multitype.adapter.ItemViewHolder;
import com.github.lany192.multitype.delegate.ItemDelegate;

public class GirlDelegate extends ItemDelegate<String> {

    public GirlDelegate(String data) {
        super(data);
    }

    @Override
    public int getSpanSize() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_girl;
    }

    @Override
    public void bind(ItemViewHolder holder, String pic, int position) {
        holder.setImage(R.id.my_image_view, pic);
    }
}
