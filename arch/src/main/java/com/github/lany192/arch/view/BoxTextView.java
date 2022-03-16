package com.github.lany192.arch.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.lany192.arch.R;

@SuppressLint("AppCompatCustomView")
public class BoxTextView extends TextView {
    /**
     * 是否中等粗细
     */
    private boolean middleBold;

    public BoxTextView(Context context) {
        super(context);
        init(null);
    }

    public BoxTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BoxTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BoxTextView);
            middleBold = typedArray.getBoolean(R.styleable.BoxTextView_text_middle_bold, middleBold);
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

    public void setTextColorId(@ColorRes int id) {
        super.setTextColor(ContextCompat.getColor(getContext(), id));
    }
}
