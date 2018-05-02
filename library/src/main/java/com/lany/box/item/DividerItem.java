package com.lany.box.item;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.View;

import com.lany.box.R;

public class DividerItem extends MultiItem {
    private int height = 4;

    @ColorInt
    private int color = Color.parseColor("#dddddd");

    public DividerItem() {
        super();
    }

    public DividerItem(int height, @ColorInt int color) {
        super();
        this.height = height;
        this.color = color;
    }

    @Override
    public int getLayoutId() {
        return R.layout.type_divider_item_view;
    }

    @Override
    public void init() {
        View view = getView(R.id.type_divider_view);
        view.getLayoutParams().height = height;
        view.setBackgroundColor(color);
    }

    public DividerItem setHeight(int height) {
        this.height = height;
        return this;
    }

    public DividerItem setColor(@ColorInt int color) {
        this.color = color;
        return this;
    }
}
