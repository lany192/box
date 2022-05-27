package com.github.lany192.text;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HtmlTextView extends BoxTextView {
    private OnUrlListener listener;

    public HtmlTextView(Context context) {
        this(context, null);
    }

    public HtmlTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HtmlTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        //添加html样式
        super.setText(Html.fromHtml(text.toString()), type);
        //添加超链接点击跳转功能
        CharSequence str = getText();
        if (str instanceof Spannable) {
            Spannable content = (Spannable) getText();
            URLSpan[] spans = content.getSpans(0, str.length(), URLSpan.class);
            SpannableStringBuilder builder = new SpannableStringBuilder(str);
            builder.clearSpans();
            for (URLSpan span : spans) {
                int startIndex = content.getSpanStart(span);
                int endIndex = content.getSpanEnd(span);
                builder.setSpan(new ClickableSpan() {

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(ds.linkColor);
                        ds.setUnderlineText(false);
                    }

                    @Override
                    public void onClick(@NonNull View widget) {
                        String title = content.subSequence(startIndex, endIndex).toString();
                        if (listener != null) {
                            listener.onUrlClick(title, span.getURL());
                        }
                    }
                }, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            super.setText(builder, type);
        }
    }

    public void setOnUrlListener(OnUrlListener listener) {
        this.listener = listener;
    }

    public interface OnUrlListener {
        void onUrlClick(String title, String url);
    }
}
