package com.lany.box.delegate;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.widget.TextView;

import com.lany.box.R;

/**
 * 分割线代理
 *
 * 高度和内容属性互斥，只能同时设置一个
 */
public class DividerDelegate extends ItemDelegate<Object> {
    private int height = 4;
    @ColorInt
    private int color = Color.parseColor("#f1f1f1");

    private String hint;

    public DividerDelegate() {
        super(null);
    }

    public DividerDelegate(int height, @ColorInt int backgroundColor) {
        super(null);
        this.height = height;
        this.color = backgroundColor;
    }

    public DividerDelegate(String hint, @ColorInt int backgroundColor) {
        super(null);
        this.hint = hint;
        this.color = backgroundColor;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_divider;
    }

    @Override
    public void init() {
        TextView hintText = getView(R.id.type_divider_view);
        hintText.setBackgroundColor(color);
        if (!TextUtils.isEmpty(hint)) {
            hintText.setText(hint);
        } else {
            hintText.getLayoutParams().height = height;
        }
    }

    public DividerDelegate setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public DividerDelegate setHeight(int height) {
        this.height = height;
        return this;
    }

    public DividerDelegate setBackgroundColor(@ColorInt int color) {
        this.color = color;
        return this;
    }
}
