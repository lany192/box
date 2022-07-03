package com.github.lany192.text.html;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

/**
 * This span defines how a table should be rendered in the HtmlTextView. The default implementation
 * is a cop-out which replaces the HTML table with some text ("[tap for table]" is the default).
 * <p/>
 * This is to be used in conjunction with the ClickableTableSpan which will redirect a click to the
 * text some application-defined action (i.e. render the raw HTML in a WebView).
 */
public class DrawTableLinkSpan extends ReplacementSpan {

    private static final String DEFAULT_TABLE_LINK_TEXT = "";
    private static float DEFAULT_TEXT_SIZE = 80f;
    private static int DEFAULT_TEXT_COLOR = Color.BLUE;

    protected String mTableLinkText = DEFAULT_TABLE_LINK_TEXT;
    protected float mTextSize = DEFAULT_TEXT_SIZE;
    protected int mTextColor = DEFAULT_TEXT_COLOR;

    // This sucks, but we need this so that each table can get drawn.
    // Otherwise, we end up with the default table link text (nothing) for earlier tables.
    public DrawTableLinkSpan newInstance() {
        final DrawTableLinkSpan drawTableLinkSpan = new DrawTableLinkSpan();
        drawTableLinkSpan.setTableLinkText(mTableLinkText);
        drawTableLinkSpan.setTextSize(mTextSize);
        drawTableLinkSpan.setTextColor(mTextColor);

        return drawTableLinkSpan;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        int width = (int) paint.measureText(mTableLinkText, 0, mTableLinkText.length());
        mTextSize = paint.getTextSize();
        return width;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        final Paint paint2 = new Paint();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(mTextColor);
        paint2.setAntiAlias(true);
        paint2.setTextSize(mTextSize);

        canvas.drawText(mTableLinkText, x, bottom, paint2);
    }

    public String getTableLinkText() {
        return mTableLinkText;
    }

    public void setTableLinkText(String tableLinkText) {
        this.mTableLinkText = tableLinkText;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }
}
