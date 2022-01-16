package com.github.lany192.box.sample.data.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.data.bean.Area;
import com.github.lany192.box.sample.databinding.ItemAreaBinding;
import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.utils.ImageUtils;
import com.hjq.toast.ToastUtils;

public class AreaBinder extends ItemBinder<Area, ItemAreaBinding> {

    @Override
    public int getSpanCount() {
        return 1;
    }

    @Override
    public void onClick(@NonNull BaseViewHolder holder, @NonNull View view, Area area, int position) {
        int count = area.getSubarea() != null ? area.getSubarea().size() : 0;
        SimpleDialog dialog = new SimpleDialog();
        dialog.setTitle("提示");
        dialog.setMessage(area.getName() + count + "个地级市");
        dialog.setRightButton("确定", () -> ToastUtils.show(area.getName()));
        dialog.setLeftButton("取消");
        dialog.show();
    }

    @NonNull
    @Override
    public ItemAreaBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        return ItemAreaBinding.inflate(layoutInflater, viewGroup, false);
    }

    @Override
    public void convert(@NonNull ItemAreaBinding binding, @NonNull BaseViewHolder holder, Area item) {
        int count = item.getSubarea() != null ? item.getSubarea().size() : 0;
        ImageUtils.show(binding.image, MockUtils.getImageUrl());
        binding.title.setText(item.getName());
        binding.desc.setText("下辖" + count + "个区/市");
    }
}
