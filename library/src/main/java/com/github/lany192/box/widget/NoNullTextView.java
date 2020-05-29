package com.github.lany192.box.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 防止空指针
 */
public class NoNullTextView extends AppCompatTextView {

    public NoNullTextView(Context context) {
        super(context);
    }

    public NoNullTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoNullTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContent(CharSequence text) {
        setText(TextUtils.isEmpty(text) ? "" : text);
    }
}
