package com.github.lany192.box.sample.ui.main.index.city;

import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.multitype.adapter.ItemViewHolder;
import com.github.lany192.multitype.delegate.ItemDelegate;
import com.hjq.toast.ToastUtils;

public class AreaDelegate extends ItemDelegate<Area> {

    public AreaDelegate(Area data) {
        super(data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_area;
    }

    @Override
    public int getSpanSize() {
        return 1;
    }

    @Override
    public void bind(ItemViewHolder holder, Area area, int position) {
        holder.setText(R.id.title, area.getName());
        int count = area.getSubarea() != null ? area.getSubarea().size() : 0;
        holder.setText(R.id.desc, "下辖" + count + "个区/市");
        holder.setImage(R.id.image, MockUtils.getImageUrl());
    }

    @Override
    public void onItemClicked(Area area, int position) {
        int count = area.getSubarea() != null ? area.getSubarea().size() : 0;
        SimpleDialog dialog = new SimpleDialog();
        dialog.setTitle("提示");
        dialog.setMessage(area.getName() + count+ "个地级市");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setRightButton("确定", () -> ToastUtils.show(area.getName()));
        dialog.setLeftButton("取消");
        dialog.show(getContext());
    }
}
