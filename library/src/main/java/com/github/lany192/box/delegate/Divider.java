package com.github.lany192.box.delegate;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.widget.TextView;

import com.github.lany192.box.R;
import com.github.lany192.box.adapter.ItemViewHolder;

/**
 * 分割线代理
 * <p>
 * 高度和内容属性互斥，只能同时设置一个
 */
public class Divider extends ItemDelegate<Object> {
    private int size = 4;
    @ColorInt
    private int color = Color.parseColor("#f1f1f1");
    @ColorInt
    private int hintColor = Color.WHITE;

    private String hint;

    public Divider() {
        super(null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_divider;
    }

    @Override
    public void init(ItemViewHolder holder, Object data, int position) {
        TextView hintText = holder.getView(R.id.type_divider_view);
        hintText.setBackgroundColor(color);
        if (!TextUtils.isEmpty(hint)) {
            hintText.setText(hint);
            hintText.setTextColor(hintColor);
        } else {
            hintText.getLayoutParams().height = size;
        }
    }

    public Divider hintColor(@ColorInt int hintColor) {
        this.hintColor = hintColor;
        return this;
    }

    public Divider hint(String hint) {
        this.hint = hint;
        return this;
    }

    public Divider size(int size) {
        this.size = size;
        return this;
    }

    public Divider color(@ColorInt int color) {
        this.color = color;
        return this;
    }
}
