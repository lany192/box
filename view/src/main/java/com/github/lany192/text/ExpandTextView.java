package com.github.lany192.text;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.lany192.view.R;

public class ExpandTextView extends BoxTextView {
    private TextView.BufferType mBufferType = TextView.BufferType.NORMAL;
    private CharSequence original;
    /**
     * 是否可以展开
     */
    private boolean expandable;

    public ExpandTextView(Context context) {
        this(context, null);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        mBufferType = type;
        setMovementMethod(LinkMovementMethod.getInstance());
        original = text;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ExpandTextView.super.setText(getNewText(), mBufferType);
            }
        });
        super.setText(original, type);
    }

    private CharSequence getNewText() {
        if (TextUtils.isEmpty(original)) {
            return original;
        }
        if (getLineCount() <= getMaxLines()) {
            return original;
        }

        String more = "更多";
        String moreStr = "... " + more;
        TextPaint textPaint = getLayout().getPaint();

        int indexStart = getLayout().getLineStart(getMaxLines() - 1);
        int indexEnd = getLayout().getLineEnd(getMaxLines() - 1);

        CharSequence lastLineStr = original.subSequence(indexStart, indexEnd);
        float lastLineWidth = textPaint.measureText(lastLineStr.toString());
        float moreWith = textPaint.measureText(moreStr);
        int offset = 0;
        float lineWidth = getLineWith();
        if (lastLineWidth + moreWith > lineWidth) {
            offset = 1;
            float offsetWidth = textPaint.measureText(original.subSequence(indexStart, indexEnd - offset).toString());
            while (offsetWidth + moreWith > lineWidth) {
                offset++;
                offsetWidth = textPaint.measureText(original.subSequence(indexStart, indexEnd - offset).toString());
            }
        }

        CharSequence fixText = removeEndLineBreak(original.subSequence(0, indexEnd - offset));
        SpannableStringBuilder builder = new SpannableStringBuilder(fixText);
        builder.append(moreStr);
        if (expandable) {
            builder.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(@NonNull TextPaint paint) {
                    paint.setColor(getColor(R.color.primary));
                    paint.setUnderlineText(false);
                }

                @Override
                public void onClick(@NonNull View widget) {
                    setMaxLines(Integer.MAX_VALUE);
                    ExpandTextView.super.setText(original, mBufferType);
                }
            }, builder.length() - more.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            builder.setSpan(new ForegroundColorSpan(getColor(R.color.primary)), builder.length() - more.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    private float getLineWith() {
        int drawWid = 0;
        Drawable[] draws = getCompoundDrawables();
        for (Drawable draw : draws) {
            if (draw != null) {
                drawWid += draw.getBounds().width();
            }
        }
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - getCompoundDrawablePadding() - drawWid;
    }

    private CharSequence removeEndLineBreak(CharSequence text) {
        while (text.toString().endsWith("\n")) {
            text = text.subSequence(0, text.length() - 1);
        }
        return text;
    }

    public void setExpandable(boolean enable) {
        this.expandable = enable;
    }
}