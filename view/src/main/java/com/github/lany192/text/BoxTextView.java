package com.github.lany192.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.utils.StringUtils;
import com.github.lany192.view.R;
import com.google.android.material.textview.MaterialTextView;

public class BoxTextView extends MaterialTextView {
    protected Logger.Builder log = XLog.tag(getClass().getSimpleName());
    /**
     * 是否中等粗细
     */
    private boolean middleBold;

    public BoxTextView(Context context) {
        this(context, null);
    }

    public BoxTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoxTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BoxTextView);
            middleBold = typedArray.getBoolean(R.styleable.BoxTextView_text_style_middle_bold, middleBold);
            typedArray.recycle();
        }
        setText(getText());
    }

    /**
     * 是否中等粗细
     */
    public void setMiddleBold(boolean middleBold) {
        this.middleBold = middleBold;
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        TextPaint paint = getPaint();
        paint.setFakeBoldText(middleBold);
    }

    public void setTextColorId(@ColorRes int id) {
        super.setTextColor(getColor(id));
    }

    public int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    public void setHtml(String text) {
        setText(StringUtils.getHtml(text));
    }
}
