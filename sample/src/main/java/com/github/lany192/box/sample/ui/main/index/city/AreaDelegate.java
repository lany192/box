package com.github.lany192.box.sample.ui.main.index.city;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.github.lany192.adapter.ItemViewBinder;
import com.github.lany192.box.items.BaseViewHolder;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.utils.ImageUtils;
public class AreaDelegate extends ItemViewBinder<Area, BaseViewHolder> {

//    @Override
//    public void onItemClicked(Area area, int position) {
//        int count = area.getSubarea() != null ? area.getSubarea().size() : 0;
//        SimpleDialog dialog = new SimpleDialog();
//        dialog.setTitle("提示");
//        dialog.setMessage(area.getName() + count + "个地级市");
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.setRightButton("确定", () -> ToastUtils.show(area.getName()));
//        dialog.setLeftButton("取消");
//        dialog.show(getContext());
//    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_area, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, Area area) {
        holder.setText(R.id.title,area.getName());
        int count = area.getSubarea() != null ? area.getSubarea().size() : 0;
        holder.setText(R.id.desc,"下辖" + count + "个区/市");

        ImageView imageView = holder.getView(R.id.image);
        ImageUtils.show(imageView, MockUtils.getImageUrl());
    }
}
