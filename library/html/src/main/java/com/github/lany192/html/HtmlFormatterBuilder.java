package com.github.lany192.html;

import android.text.Html.ImageGetter;

import androidx.annotation.Nullable;

public class HtmlFormatterBuilder {

    private String html;
    private ImageGetter imageGetter;
    private ClickableTableSpan clickableTableSpan;
    private DrawTableLinkSpan drawTableLinkSpan;
    private OnClickATagListener onClickATagListener;
    private float indent = 24.0f;
    private boolean removeTrailingWhiteSpace = true;

    public String getHtml() {
        return html;
    }

    public HtmlFormatterBuilder setHtml(@Nullable final String html) {
        this.html = html;
        return this;
    }

    public ImageGetter getImageGetter() {
        return imageGetter;
    }

    public HtmlFormatterBuilder setImageGetter(@Nullable final ImageGetter imageGetter) {
        this.imageGetter = imageGetter;
        return this;
    }

    public ClickableTableSpan getClickableTableSpan() {
        return clickableTableSpan;
    }

    public HtmlFormatterBuilder setClickableTableSpan(@Nullable final ClickableTableSpan clickableTableSpan) {
        this.clickableTableSpan = clickableTableSpan;
        return this;
    }

    public DrawTableLinkSpan getDrawTableLinkSpan() {
        return drawTableLinkSpan;
    }

    public HtmlFormatterBuilder setDrawTableLinkSpan(@Nullable final DrawTableLinkSpan drawTableLinkSpan) {
        this.drawTableLinkSpan = drawTableLinkSpan;
        return this;
    }

    public OnClickATagListener getOnClickATagListener() {
        return onClickATagListener;
    }

    public void setOnClickATagListener(OnClickATagListener onClickATagListener) {
        this.onClickATagListener = onClickATagListener;
    }

    public float getIndent() {
        return indent;
    }

    public HtmlFormatterBuilder setIndent(final float indent) {
        this.indent = indent;
        return this;
    }

    public boolean isRemoveTrailingWhiteSpace() {
        return removeTrailingWhiteSpace;
    }

    public HtmlFormatterBuilder setRemoveTrailingWhiteSpace(final boolean removeTrailingWhiteSpace) {
        this.removeTrailingWhiteSpace = removeTrailingWhiteSpace;
        return this;
    }
}