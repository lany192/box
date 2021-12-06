package com.github.lany192.box.sample.ui.main.discover;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.github.lany192.adapter.ItemViewBinder;
import com.github.lany192.box.items.BaseViewHolder;
import com.github.lany192.box.sample.R;
import com.github.lany192.utils.ImageUtils;

public class ImageDelegate extends ItemViewBinder<String, BaseViewHolder> {

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, String url) {
        ImageView imageView = holder.getView(R.id.image);
        ImageUtils.show(imageView, url);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_image, parent, false);
        return new BaseViewHolder(view);
    }
}
