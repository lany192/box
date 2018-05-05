package com.lany.box.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

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

    public void setTextNoNull(CharSequence text) {
        setText(TextUtils.isEmpty(text) ? "" : text);
    }
}
