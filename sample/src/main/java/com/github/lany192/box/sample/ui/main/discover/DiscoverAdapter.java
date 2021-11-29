package com.github.lany192.box.sample.ui.main.discover;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.box.helper.ImageLoader;
import com.github.lany192.box.sample.R;

import java.util.List;

public class DiscoverAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DiscoverAdapter(@Nullable List<String> data) {
        super(R.layout.item_area, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String area) {
        holder.setText(R.id.title, "图片");
        ImageView imageView = holder.getView(R.id.image);
        ImageLoader.get().show(imageView,area);
    }
}
