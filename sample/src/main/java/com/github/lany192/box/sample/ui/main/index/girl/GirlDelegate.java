package com.github.lany192.box.sample.ui.main.index.girl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.drakeet.multitype.ItemViewBinder;
import com.github.lany192.box.items.BaseViewHolder;
import com.github.lany192.box.sample.R;
import com.github.lany192.utils.ImageUtils;

public class GirlDelegate extends ItemViewBinder<String, BaseViewHolder> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_girl, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, String url) {
        ImageView imageView = holder.getView(R.id.image);
        ImageUtils.show(imageView, url);
    }
}
