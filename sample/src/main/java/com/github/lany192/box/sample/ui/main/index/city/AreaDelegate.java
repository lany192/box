package com.github.lany192.box.sample.ui.main.index.city;

import android.view.LayoutInflater;

import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.databinding.ItemAreaBinding;
import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.multitype.delegate.ItemDelegate;
import com.github.lany192.utils.ImageUtils;
import com.hjq.toast.ToastUtils;

public class AreaDelegate extends ItemDelegate<Area, ItemAreaBinding> {

    public AreaDelegate(Area data) {
        super(data);
    }

    @Override
    public ItemAreaBinding getViewBinding(LayoutInflater inflater) {
        return ItemAreaBinding.inflate(inflater);
    }

    @Override
    public void onBind(ItemAreaBinding binding, Area area, int position) {
        binding.title.setText(area.getName());
        int count = area.getSubarea() != null ? area.getSubarea().size() : 0;
        binding.desc.setText("下辖" + count + "个区/市");
        ImageUtils.show(binding.image, MockUtils.getImageUrl());
    }

    @Override
    public int getSpanSize() {
        return 1;
    }

    @Override
    public void onItemClicked(Area area, int position) {
        int count = area.getSubarea() != null ? area.getSubarea().size() : 0;
        SimpleDialog dialog = new SimpleDialog();
        dialog.setTitle("提示");
        dialog.setMessage(area.getName() + count + "个地级市");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setRightButton("确定", () -> ToastUtils.show(area.getName()));
        dialog.setLeftButton("取消");
        dialog.show(getContext());
    }
}
