package com.github.lany192.textview;

import android.content.Context;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.github.lany192.view.R;

/**
 * 设置了最大行数的TextView,省略号之后追加“全文”
 */
public class EllipsizeTextView extends BoxTextView {
    private TextView.BufferType mBufferType = TextView.BufferType.NORMAL;
    private Layout mLayout;
    private CharSequence mOrigText;

    public EllipsizeTextView(Context context) {
        this(context, null);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                setTextInternal(getNewTextByConfig(), mBufferType);
            }
        });
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
        String mToExpandHint = "全文";
        String mGapToExpandHint = " ";
        String mEllipsisHint = "...";
        int indexEndTrimmed = indexEnd - getLengthOfString(mEllipsisHint) - (getLengthOfString(mToExpandHint) + getLengthOfString(mGapToExpandHint));
        if (indexEndTrimmed <= indexStart) {
            indexEndTrimmed = indexEnd;
        }
        int remainWidth = getValidLayout().getWidth() -
                (int) (textPaint.measureText(mOrigText.subSequence(indexStart, indexEndTrimmed).toString()) + 0.5);
        float widthTailReplaced = textPaint.measureText(getContentOfString(mEllipsisHint)
                + ((getContentOfString(mToExpandHint) + getContentOfString(mGapToExpandHint))));

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
        SpannableStringBuilder ssbShrink = new SpannableStringBuilder(fixText);
        ssbShrink.append(mEllipsisHint);
        ssbShrink.append(getContentOfString(mGapToExpandHint));
        ssbShrink.append(getContentOfString(mToExpandHint));
        ssbShrink.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary)), ssbShrink.length() - getLengthOfString(mToExpandHint), ssbShrink.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssbShrink;
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
        mOrigText = text;
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