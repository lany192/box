package com.github.lany192.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.lany192.view.R;
import com.google.android.material.button.MaterialButton;

/**
 * 1.防止出现空指针
 * 2.显示中等粗字体
 */
public class BoxButton extends MaterialButton {
    /**
     * 是否中等粗细
     */
    private boolean middleBold;

    public BoxButton(Context context) {
        this(context, null);
    }

    public BoxButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoxButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BoxButton);
            middleBold = typedArray.getBoolean(R.styleable.BoxButton_text_style_middle_bold, middleBold);
            typedArray.recycle();
        }
        setText(getText());
    }

    /**
     * 是否中等粗细
     */
    public void setTextMiddleBold(boolean middleBold) {
        this.middleBold = middleBold;
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        //防止空指针
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        super.setText(text, type);
        TextPaint paint = getPaint();
        paint.setFakeBoldText(middleBold);
    }

    /**
     * 设置颜色
     *
     * @param id 资源id
     */
    public void setTextColorId(@ColorRes int id) {
        super.setTextColor(ContextCompat.getColor(getContext(), id));
    }
}
