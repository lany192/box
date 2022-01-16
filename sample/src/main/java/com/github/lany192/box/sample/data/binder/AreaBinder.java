package com.github.lany192.box.sample.data.binder;

import android.view.View;

import androidx.annotation.NonNull;

import com.github.lany192.arch.items.BindingItemBinder;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.data.bean.Area;
import com.github.lany192.box.sample.databinding.ItemAreaBinding;
import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.utils.ImageUtils;
import com.hjq.toast.ToastUtils;

public class AreaBinder extends BindingItemBinder<Area, ItemAreaBinding> {

    @Override
    public void onClick(@NonNull BindingHolder<ItemAreaBinding> holder, @NonNull View view, Area area, int position) {
        int count = area.getSubarea() != null ? area.getSubarea().size() : 0;
        SimpleDialog dialog = new SimpleDialog();
        dialog.setTitle("提示");
        dialog.setMessage(area.getName() + count + "个地级市");
        dialog.setRightButton("确定", () -> ToastUtils.show(area.getName()));
        dialog.setLeftButton("取消");
        dialog.show();
    }

    @Override
    public void convert(@NonNull BindingHolder<ItemAreaBinding> holder, Area item) {
        int count = item.getSubarea() != null ? item.getSubarea().size() : 0;
        ImageUtils.show(holder.getBinding().image, MockUtils.getImageUrl());
        holder.getBinding().title.setText(item.getName());
        holder.getBinding().desc.setText("下辖" + count + "个区/市");
    }
}
