package com.github.lany192.box.sample.ui.main.discover;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.box.helper.ImageLoader;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Area;

import java.util.List;

public class DiscoverAdapter extends BaseQuickAdapter<Area, BaseViewHolder> {

    public DiscoverAdapter(@Nullable List<Area> data) {
        super(R.layout.item_area, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Area area) {
        holder.setText(R.id.item_area_title, area.getName());
        int count = area.getSubarea() != null ? area.getSubarea().size() : 0;
        holder.setText(R.id.item_poster_running_time, "下辖" + count + "个区/市");
        ImageView imageView = holder.getView(R.id.item_poster_post);
        ImageLoader.get().show(imageView,"https://drimg02.scbao.com/190602/330471-1Z60214395123.jpg");
    }
}
