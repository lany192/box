package com.github.lany192.text;

import android.content.Context;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.lany192.view.R;

/**
 * 可展开 TextView
 * 通过maxLines控制收起时的最大行数
 */
public class ExpandableTextView extends BoxTextView {
    private final String MORE_TEXT = "更多";
    private TextView.BufferType mBufferType = TextView.BufferType.NORMAL;
    private Layout mLayout;
    private CharSequence mOrigText;
    /**
     * 是否已经展开
     */
    private boolean expand;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMovementMethod(LinkMovementMethod.getInstance());
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (expand) {
                    setTextInternal(getOriginalText(), mBufferType);
                } else {
                    setTextInternal(getNewTextByConfig(), mBufferType);
                }
            }
        });
    }

    private CharSequence getOriginalText() {
        if (TextUtils.isEmpty(mOrigText)) {
            return mOrigText;
        }
        return mOrigText;
    }

    private CharSequence getNewTextByConfig() {
        if (TextUtils.isEmpty(mOrigText)) {
            return mOrigText;
        }
        mLayout = getLayout();
        int mLayoutWidth = 0;
        if (mLayout != null) {
            mLayoutWidth = mLayout.getWidth();
        }

        if (mLayoutWidth <= 0) {
            if (getWidth() == 0) {
                return mOrigText;
            } else {
                mLayoutWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            }
        }
        TextPaint textPaint = getPaint();

        mLayout = new DynamicLayout(mOrigText, textPaint, mLayoutWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        int mTextLineCount = mLayout.getLineCount();

        if (mTextLineCount <= getMaxLines()) {
            return mOrigText;
        }
        int indexEnd = getValidLayout().getLineEnd(getMaxLines() - 1);
        int indexStart = getValidLayout().getLineStart(getMaxLines() - 1);
        String mGapToExpandHint = " ";
        String mEllipsisHint = "...";
        int indexEndTrimmed = indexEnd - getLengthOfString(mEllipsisHint) - (getLengthOfString(MORE_TEXT) + getLengthOfString(mGapToExpandHint));
        if (indexEndTrimmed <= indexStart) {
            indexEndTrimmed = indexEnd;
        }
        int remainWidth = getValidLayout().getWidth() -
                (int) (textPaint.measureText(mOrigText.subSequence(indexStart, indexEndTrimmed).toString()) + 0.5);
        float widthTailReplaced = textPaint.measureText(getContentOfString(mEllipsisHint)
                + ((getContentOfString(MORE_TEXT) + getContentOfString(mGapToExpandHint))));

        int indexEndTrimmedRevised = indexEndTrimmed;
        int extraOffset = 0;
        int extraWidth = 0;
        if (remainWidth > widthTailReplaced) {
            while (remainWidth > widthTailReplaced + extraWidth) {
                extraOffset++;
                if (indexEndTrimmed + extraOffset <= mOrigText.length()) {
                    extraWidth = (int) (textPaint.measureText(
                            mOrigText.subSequence(indexEndTrimmed, indexEndTrimmed + extraOffset).toString()) + 0.5);
                } else {
                    break;
                }
            }
            indexEndTrimmedRevised += extraOffset - 1;
        } else {
            while (remainWidth + extraWidth < widthTailReplaced) {
                extraOffset--;
                if (indexEndTrimmed + extraOffset > indexStart) {
                    extraWidth = (int) (textPaint.measureText(mOrigText.subSequence(indexEndTrimmed + extraOffset, indexEndTrimmed).toString()) + 0.5);
                } else {
                    break;
                }
            }
            indexEndTrimmedRevised += extraOffset;
        }

        CharSequence fixText = removeEndLineBreak(mOrigText.subSequence(0, indexEndTrimmedRevised));
        SpannableStringBuilder builder = new SpannableStringBuilder(fixText);
        builder.append(mEllipsisHint);
        builder.append(getContentOfString(mGapToExpandHint));
        builder.append(getContentOfString(MORE_TEXT));
        builder.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint paint) {
                paint.setColor(getResources().getColor(R.color.primary));
                paint.setUnderlineText(false);
            }

            @Override
            public void onClick(@NonNull View widget) {
                expand = true;
                setMaxLines(Integer.MAX_VALUE);
                setTextInternal(getOriginalText(), mBufferType);
            }
        }, builder.length() - getLengthOfString(MORE_TEXT), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private CharSequence removeEndLineBreak(CharSequence text) {
        while (text.toString().endsWith("\n")) {
            text = text.subSequence(0, text.length() - 1);
        }
        return text;
    }

    private Layout getValidLayout() {
        return mLayout != null ? mLayout : getLayout();
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        if (TextUtils.isEmpty(mOrigText)) {
            mOrigText = text;
        }
        mBufferType = type;
        setTextInternal(getNewTextByConfig(), type);
    }

    private void setTextInternal(CharSequence text, TextView.BufferType type) {
        super.setText(text, type);
    }

    private int getLengthOfString(String string) {
        if (string == null)
            return 0;
        return string.length();
    }

    private String getContentOfString(String string) {
        if (string == null)
            return "";
        return string;
    }
}