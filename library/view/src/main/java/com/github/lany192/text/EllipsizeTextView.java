package com.github.lany192.text;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.lany192.view.R;
import com.hjq.toast.ToastUtils;

public class EllipsizeTextView extends BoxTextView {
    private TextView.BufferType mBufferType = TextView.BufferType.NORMAL;
    private CharSequence original;

    public EllipsizeTextView(Context context) {
        this(context, null);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        mBufferType = type;
        original = text;
        setMovementMethod(LinkMovementMethod.getInstance());
        setHighlightColor(Color.TRANSPARENT);
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        //添加超链接点击跳转功能
        URLSpan[] spans = builder.getSpans(0, text.length(), URLSpan.class);
        for (URLSpan span : spans) {
            builder.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(Color.RED);
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(@NonNull View widget) {
                    ToastUtils.show("测试");
                }
            }, builder.getSpanStart(span), builder.getSpanEnd(span), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.removeSpan(span);
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                EllipsizeTextView.super.setText(getNewText(), mBufferType);
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

        String more = "全文";
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
        builder.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint paint) {
                paint.setColor(getColor(R.color.primary));
                paint.setUnderlineText(false);
            }

            @Override
            public void onClick(@NonNull View widget) {
                setMaxLines(Integer.MAX_VALUE);
                EllipsizeTextView.super.setText(original, mBufferType);
            }
        }, builder.length() - more.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
}