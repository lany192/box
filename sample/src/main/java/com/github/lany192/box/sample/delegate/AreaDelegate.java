package com.github.lany192.box.sample.delegate;

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
    public void bind(ItemViewHolder holder, Area area, int position) {
        holder.setText(R.id.title, area.getName());
    }

    @Override
    public void onItemClicked(Area area, int position) {
        SimpleDialog dialog = new SimpleDialog();
        dialog.setTitle("提示");
        dialog.setMessage(area.getName() + (area.getSubarea().size() + 1) + "个地级市");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setRightButton("确定", () -> ToastUtils.show(area.getName()));
        dialog.setLeftButton("取消");
        dialog.show(getContext());
    }
}
