package com.github.lany192.box.sample.delegate;

import com.github.lany192.box.adapter.ItemViewHolder;
import com.github.lany192.box.delegate.ItemDelegate;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Area;

public class AreaDelegate extends ItemDelegate<Area> {

    public AreaDelegate(Area data) {
        super(data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_area;
    }

    @Override
    public void init(ItemViewHolder holder, Area area, int position) {
        holder.setText(R.id.item_area_title, area.getName());
    }
}
