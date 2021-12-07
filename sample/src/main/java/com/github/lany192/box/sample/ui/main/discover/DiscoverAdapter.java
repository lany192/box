package com.github.lany192.box.sample.ui.main.discover;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.box.sample.R;
import com.github.lany192.utils.ImageUtils;

import java.util.List;

public class DiscoverAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DiscoverAdapter(@Nullable List<String> data) {
        super(R.layout.item_disvocer, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String url) {
        ImageUtils.show(holder.getView(R.id.image), url);
    }
}
