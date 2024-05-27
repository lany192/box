package com.github.lany192.html;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.BulletSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Class to use Numbered Lists in TextViews.
 * The span works the same as {@link BulletSpan} and all lines of the entry have
 * the same leading margin.
 */
public class NumberSpan extends BulletSpan {
    public static final int STANDARD_GAP_WIDTH = 10;
    private final int mNumberGapWidth;
    private final String mNumber;

    public NumberSpan(int gapWidth, int number) {
        super();
        mNumberGapWidth = gapWidth;
        mNumber = Integer.toString(number).concat(".");
    }

    public NumberSpan(int number) {
        this(STANDARD_GAP_WIDTH, number);
    }

    public NumberSpan(Parcel src) {
        super(src);
        mNumberGapWidth = src.readInt();
        mNumber = src.readString();
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mNumberGapWidth);
        dest.writeString(mNumber);
    }

    public int getLeadingMargin(boolean first) {
        return 2 * STANDARD_GAP_WIDTH + mNumberGapWidth;
    }

    @Override
    public void drawLeadingMargin(@NonNull Canvas c, @NonNull Paint p, int x, int dir,
                                  int top, int baseline, int bottom, @NonNull CharSequence text,
                                  int start, int end, boolean first, @Nullable Layout l) {
        if (((Spanned) text).getSpanStart(this) == start) {
            Paint.Style style = p.getStyle();

            p.setStyle(Paint.Style.FILL);

            if (c.isHardwareAccelerated()) {
                c.save();
                c.drawText(mNumber, x + dir, baseline, p);
                c.restore();
            } else {
                c.drawText(mNumber, x + dir, (top + bottom) / 2.0f, p);
            }

            p.setStyle(style);
        }
    }
}
