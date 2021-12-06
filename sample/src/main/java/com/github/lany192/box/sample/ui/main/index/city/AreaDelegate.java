package com.github.lany192.box.sample.ui.main.index.city;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.lany192.box.items.ItemDelegate;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.databinding.ItemAreaBinding;
import com.github.lany192.utils.ImageUtils;

public class AreaDelegate extends ItemDelegate<Area, ItemAreaBinding> {

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


    @Override
    public int getSpanCount() {
        return 1;
    }

    @Override
    public ItemAreaBinding getViewBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemAreaBinding.inflate(inflater, parent, false);
    }

    @Override
    public void onBind(ItemAreaBinding binding, Area item, int position) {
        int count = item.getSubarea() != null ? item.getSubarea().size() : 0;

        ImageUtils.show(binding.image, MockUtils.getImageUrl());
        binding.title.setText(item.getName());
        binding.desc.setText("下辖" + count + "个区/市");
    }
}
