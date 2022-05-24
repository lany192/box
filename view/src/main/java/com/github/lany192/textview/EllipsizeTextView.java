package com.github.lany192.textview;

import android.content.Context;
import android.text.DynamicLayout;
import android.text.Layout;
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

import com.elvishew.xlog.XLog;
import com.github.lany192.view.R;

/**
 * 设置了最大行数的TextView,省略号之后追加“全文”
 */
public class EllipsizeTextView extends BoxTextView {
    private TextView.BufferType mBufferType = TextView.BufferType.NORMAL;
    private Layout mLayout;
    private CharSequence mOrigText;
    private final int mOrigMaxLines;
    String PACK_UP = "收起";
    String mToExpandHint = "全文";
    /**
     * 是否已经展开
     */
    private boolean expand;
    private boolean expandable;

    public EllipsizeTextView(Context context) {
        this(context, null);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mOrigMaxLines = getMaxLines();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                updateView();
            }
        });
    }

    private void updateView() {
        if (expandable) {
            if (expand) {
                setTextInternal(getOriginalText(), mBufferType);
            } else {
                setTextInternal(getNewTextByConfig(), mBufferType);
            }
        } else {
            setTextInternal(getNewTextByConfig(), mBufferType);
        }
    }

    /**
     * 是否可以展开
     */
    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
        if (expandable) {
            setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private CharSequence getOriginalText() {
        if (TextUtils.isEmpty(mOrigText)) {
            return mOrigText;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(mOrigText);
        builder.append(getContentOfString(PACK_UP));
        builder.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint paint) {
                paint.setColor(getResources().getColor(R.color.primary));
                paint.setUnderlineText(false);
            }

            @Override
            public void onClick(@NonNull View widget) {
                expand = false;
                setMaxLines(mOrigMaxLines);
                setTextInternal(getNewTextByConfig(), mBufferType);
            }
        }, builder.length() - getLengthOfString(PACK_UP), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
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
        int indexEndTrimmed = indexEnd - getLengthOfString(mEllipsisHint) - (getLengthOfString(mToExpandHint) + getLengthOfString(mGapToExpandHint));
        if (indexEndTrimmed <= indexStart) {
            indexEndTrimmed = indexEnd;
        }
        XLog.i("测试：最后一行开始和结束区间，" + indexStart + "，" + indexEnd + "，处理后的结束索引：" + indexEndTrimmed);

        //遗留宽度
        int remainWidth = getValidLayout().getWidth() - (int) (textPaint.measureText(mOrigText.subSequence(indexStart, indexEndTrimmed).toString()) + 0.5);
        //追加文字的宽度
        float widthTailReplaced = textPaint.measureText(getContentOfString(mEllipsisHint) + ((getContentOfString(mToExpandHint) + getContentOfString(mGapToExpandHint))));

        int indexEndTrimmedRevised = indexEndTrimmed;
        int extraOffset = 0;
        int extraWidth = 0;

        XLog.i("测试：2222222222222222222222222，" + remainWidth + "，" + widthTailReplaced + "，" + indexEndTrimmed);
        if (remainWidth > widthTailReplaced) {
            XLog.i("测试：333333333333333333333");
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
            XLog.i("测试：4444444444444444444444444");
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
        XLog.i("测试：555" + mOrigText.subSequence(0, indexEndTrimmedRevised));


        CharSequence fixText = removeEndLineBreak(mOrigText.subSequence(0, indexEndTrimmedRevised));

        SpannableStringBuilder builder = new SpannableStringBuilder(fixText);
        builder.append(mEllipsisHint);
        builder.append(getContentOfString(mGapToExpandHint));
        builder.append(getContentOfString(mToExpandHint));
        if (expandable) {
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
            }, builder.length() - getLengthOfString(mToExpandHint), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary)), builder.length() - getLengthOfString(mToExpandHint), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
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