package com.github.lany192.edittext;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

/**
 * 防止出现空指针
 */
public class BoxEditText extends AppCompatEditText {

    public BoxEditText(Context context) {
        this(context, null);
    }

    public BoxEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoxEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        //防止空指针
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        super.setText(text, type);
    }
}
