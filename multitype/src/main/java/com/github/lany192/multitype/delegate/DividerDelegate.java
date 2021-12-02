package com.github.lany192.multitype.delegate;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.github.lany192.multitype.R;
import com.github.lany192.multitype.adapter.ItemViewHolder;


/**
 * 分割线代理
 * <p>
 * 高度和内容属性互斥，只能同时设置一个
 */
public class DividerDelegate extends ItemDelegate<Object> {
    private int size = 4;
    @ColorInt
    private int color = Color.parseColor("#f1f1f1");
    @ColorInt
    private int hintColor = Color.WHITE;

    private String hint;

    public DividerDelegate() {
        super(null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_divider;
    }

    @Override
    public void bind(ItemViewHolder holder, Object data, int position) {
        TextView hintText = holder.getView(R.id.type_divider_view);
        hintText.setBackgroundColor(color);
        if (!TextUtils.isEmpty(hint)) {
            hintText.setText(hint);
            hintText.setTextColor(hintColor);
        } else {
            hintText.getLayoutParams().height = size;
        }
    }

    public DividerDelegate hintColor(@ColorInt int hintColor) {
        this.hintColor = hintColor;
        return this;
    }

    public DividerDelegate hint(String hint) {
        this.hint = hint;
        return this;
    }

    public DividerDelegate size(int size) {
        this.size = size;
        return this;
    }

    public DividerDelegate color(@ColorInt int color) {
        this.color = color;
        return this;
    }
}
