package com.github.lany192.box.delegate;

import com.github.lany192.box.R;
import com.github.lany192.box.adapter.ItemViewHolder;

public class HeightDelegate extends ItemDelegate<Integer> {

    public HeightDelegate(Integer data) {
        super(data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_height;
    }

    @Override
    public void bind(ItemViewHolder holder, Integer height, int position) {
        holder.itemView.getLayoutParams().height = height;
    }
}
