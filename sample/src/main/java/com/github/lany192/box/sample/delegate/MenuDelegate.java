package com.github.lany192.box.sample.delegate;

import com.github.lany192.box.adapter.ItemViewHolder;
import com.github.lany192.box.delegate.ItemDelegate;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Menu;
import com.hjq.toast.ToastUtils;

public class MenuDelegate extends ItemDelegate<Menu> {

    public MenuDelegate(Menu data) {
        super(data);
    }

    @Override
    public int getSpanSize() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_menu;
    }

    @Override
    public void bind(ItemViewHolder holder, Menu area, int position) {
        holder.setText(R.id.item_menu_title, area.getName());
    }

    @Override
    public void onItemClicked(Menu area, int position) {
        ToastUtils.show(area.getName());
    }
}
