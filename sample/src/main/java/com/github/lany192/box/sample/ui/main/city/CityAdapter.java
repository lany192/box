package com.github.lany192.box.sample.ui.main.city;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Area;

import java.util.List;

public class CityAdapter extends BaseQuickAdapter<Area, BaseViewHolder> {

    public CityAdapter(@Nullable List<Area> data) {
        super(R.layout.item_area, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Area area) {
        holder.setText(R.id.item_area_title, area.getName());
        holder.setImageResource(R.id.item_poster_post, R.mipmap.ic_launcher);
    }
}
