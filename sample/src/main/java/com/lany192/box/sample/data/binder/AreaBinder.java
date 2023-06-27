package com.lany192.box.sample.data.binder;

import android.view.View;

import androidx.annotation.NonNull;

import com.github.lany192.arch.adapter.BindingHolder;
import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.utils.ImageUtils;
import com.hjq.toast.ToastUtils;
import com.lany192.box.network.data.bean.Area;
import com.lany192.box.sample.MockUtils;
import com.lany192.box.sample.databinding.ItemAreaBinding;

public class AreaBinder extends ItemBinder<Area, ItemAreaBinding> {

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
    public void convert(@NonNull ItemAreaBinding binding, Area item, int position) {
        int count = item.getSubarea() != null ? item.getSubarea().size() : 0;
        ImageUtils.show(binding.image, MockUtils.getImageUrl());
        binding.title.setText(item.getName());
        binding.desc.setText("下辖" + count + "个区/市");
    }
}
