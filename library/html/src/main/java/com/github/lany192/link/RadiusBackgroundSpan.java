package com.github.lany192.link;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;
import android.util.TypedValue;

/**
 * 圆角背景Span
 */
public class RadiusBackgroundSpan extends ReplacementSpan {
    private final int mColor;
    private final int mRadius;
    private final int topBottomPadding;
    private int mSize;

    public RadiusBackgroundSpan(int color, float radiusDp, float paddingDp) {
        this.mColor = color;
        this.mRadius = dp2px(radiusDp);
        this.topBottomPadding = dp2px(paddingDp);
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mSize = (int) (paint.measureText(text, start, end) + 2 * mRadius);
        //mSize就是span的宽度，span有多宽，开发者可以在这里随便定义规则
        //我的规则：这里text传入的是SpannableString，start，end对应setSpan方法相关参数
        //可以根据传入起始截至位置获得截取文字的宽度，最后加上左右两个圆角的半径得到span宽度
        return mSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int color = paint.getColor();//保存文字颜色
        paint.setColor(mColor);//设置背景颜色
        paint.setAntiAlias(true);// 设置画笔的锯齿效果
        RectF oval = new RectF(x, y + paint.ascent() - topBottomPadding, x + mSize, y + paint.descent() + topBottomPadding);
        //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
        canvas.drawRoundRect(oval, mRadius, mRadius, paint);//绘制圆角矩形，第二个参数是x半径，第三个参数是y半径
        paint.setColor(color);//恢复画笔的文字颜色
        canvas.drawText(text, start, end, x + mRadius, y, paint);//绘制文字
    }
}