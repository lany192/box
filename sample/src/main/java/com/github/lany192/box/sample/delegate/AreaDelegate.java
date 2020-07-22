package com.github.lany192.box.sample.delegate;

import androidx.fragment.app.FragmentActivity;

import com.github.lany192.box.adapter.ItemViewHolder;
import com.github.lany192.box.delegate.ItemDelegate;
import com.github.lany192.box.dialog.SimpleDialog;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Area;
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
        holder.setText(R.id.item_area_title, area.getName());
    }

    @Override
    public void onItemClicked(Area area, int position) {
        SimpleDialog dialog = new SimpleDialog();
        dialog.setTitle("提示");
        dialog.setMessage(area.getName() + (area.getChildren().size() + 1) + "个地级市");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setRightBtn("确定", () -> ToastUtils.show(area.getName()));
        dialog.setLeftBtn("取消");
        dialog.show((FragmentActivity) getContext());
    }
}
