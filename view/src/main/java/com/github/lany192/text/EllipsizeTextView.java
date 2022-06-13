package com.github.lany192.text;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.lany192.view.R;
import com.hjq.toast.ToastUtils;

public class EllipsizeTextView extends BoxTextView {
    private final String moreText = "...全文";

    public EllipsizeTextView(Context context) {
        this(context, null);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMovementMethod(LinkMovementMethod.getInstance());
        setHighlightColor(Color.TRANSPARENT);
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        //添加超链接点击跳转功能
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        URLSpan[] spans = builder.getSpans(0, text.length(), URLSpan.class);
        for (URLSpan span : spans) {
            int startIndex = builder.getSpanStart(span);
            int endIndex = builder.getSpanEnd(span);
            builder.setSpan(new ClickableSpan() {

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(getResources().getColor(R.color.primary));
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(@NonNull View widget) {
                    String title = builder.subSequence(startIndex, endIndex).toString();
                    log.i("点击超链接：" + title + span.getURL());
                    ToastUtils.show("点击超链接：" + title + span.getURL());
                }
            }, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.removeSpan(span);
        }
        super.setText(builder, type);
    }

    private int getTextNeedWidth(String text) {
        float[] widths = new float[text.length()];
        getPaint().getTextWidths(text, widths);
        int nowLength = 0;
        for (float width : widths) {
            nowLength += width;
        }
        return nowLength;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getLineCount() > getMaxLines()) {
            setText(clipText());
        }
    }

    private SpannableStringBuilder clipText() {
        int indexEnd = getLayout().getLineEnd(getMaxLines() - 1);
        CharSequence temp = getText().subSequence(0, indexEnd);

        int moreWidth = (int) Math.ceil(getTextNeedWidth(moreText));

        int offsetWidth = getTextNeedWidth(String.valueOf(temp.charAt(indexEnd - 1)));
        int countEmoji = 0;
        int offset = 1;
        while (indexEnd > offset && offsetWidth <= moreWidth) {
            boolean isEmoji = isEmoji(temp.charAt(indexEnd - offset));
            if (isEmoji) {
                countEmoji += 1;
            }
            offset++;
            if (indexEnd > offset) {
                if (countEmoji % 2 == 0) {
                    char charText = temp.charAt(indexEnd - offset);
                    offsetWidth += getTextNeedWidth(String.valueOf(charText));
                    if (offsetWidth > moreWidth && isEmoji(charText)) {
                        offset++;
                    }
                }
            } else {
                char charText = temp.charAt(indexEnd - offset);
                offsetWidth += getTextNeedWidth(String.valueOf(charText));
            }
            log.i("测试indexEnd:" + indexEnd);
            log.i("测试offset:" + offset);
            log.i("测试offsetWidth:" + offsetWidth);
            log.i("测试moreWidth:" + moreWidth);
        }
        CharSequence clip = temp.subSequence(0, indexEnd - offset);


        SpannableString sb = new SpannableString(moreText);
        sb.setSpan(new ForegroundColorSpan(Color.BLUE), 3, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sb.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.primary));
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(@NonNull View widget) {
                ToastUtils.show("点击了全文");
            }
        }, 3, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(clip);
        stringBuilder.append(sb);
        return stringBuilder;
    }

    private boolean isEmoji(char str) {
        return str == 0x0 || str == 0x9 || str == 0xA
                || str == 0xD || str >= 0x20 && str <= 0xD7FF
                || str >= 0xE000 && str <= 0xFFFD;
    }
}