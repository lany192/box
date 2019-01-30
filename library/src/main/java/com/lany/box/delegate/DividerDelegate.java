package com.lany.box.delegate;

import android.graphics.Color;
import androidx.annotation.ColorInt;
import android.view.View;

import com.lany.box.R;

/**
 * 分割线代理
 */
public class DividerDelegate extends ItemDelegate<Object> {
    private int height = 4;

    @ColorInt
    private int color = Color.parseColor("#dddddd");

    public DividerDelegate() {
        super(null);
    }

    public DividerDelegate(int height, @ColorInt int color) {
        super(null);
        this.height = height;
        this.color = color;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_divider;
    }

    @Override
    public void init() {
        View view = getView(R.id.type_divider_view);
        view.getLayoutParams().height = height;
        view.setBackgroundColor(color);
    }

    public DividerDelegate setHeight(int height) {
        this.height = height;
        return this;
    }

    public DividerDelegate setColor(@ColorInt int color) {
        this.color = color;
        return this;
    }
}
