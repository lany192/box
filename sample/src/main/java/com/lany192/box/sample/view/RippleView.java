package com.lany192.box.sample.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.github.lany192.utils.DensityUtils;
import com.lany192.box.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 涟漪效果
 */
public class RippleView extends View {
    // 画笔对象
    private Paint mPaint;

    // View宽
    private float mWidth;

    // View高
    private float mHeight;

    // 声波的圆圈集合
    private List<Rectangle> mRipples;

    // 圆圈扩散的速度
    private float mSpeed;

    // 圆圈之间的密度
    private int mDensity;

    // 圆圈的颜色
    private int mColor;

    // 圆圈是否为填充模式
    private boolean mIsFill;

    // 圆圈是否为渐变模式
    private boolean mIsAlpha;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray tya = context.obtainStyledAttributes(attrs, R.styleable.mRippleView);
        mColor = tya.getColor(R.styleable.mRippleView_cColor, Color.BLUE);
        mSpeed = tya.getFloat(R.styleable.mRippleView_cSpeed, 0.6f);
        mDensity = tya.getInt(R.styleable.mRippleView_cDensity, 10);
        mIsFill = tya.getBoolean(R.styleable.mRippleView_cIsFill, false);
        mIsAlpha = tya.getBoolean(R.styleable.mRippleView_cIsAlpha, true);
        tya.recycle();

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(DensityUtils.dp2px(1));
        if (mIsFill) {
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        // 添加第一个圆圈
        mRipples = new ArrayList<>();
        mRipples.add(new Rectangle(DensityUtils.dp2px(48), DensityUtils.dp2px(24), 255));

        mDensity = DensityUtils.dp2px(mDensity);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        // 处理每个圆的宽度和透明度
        for (int i = 0; i < mRipples.size(); i++) {
            Rectangle circle = mRipples.get(i);
            mPaint.setAlpha(circle.alpha);// （透明）0~255（不透明）

            float radius = circle.width - mPaint.getStrokeWidth();

            float ratio = 0.5f;

            float left = mWidth / 2 - circle.width;
            float top = mHeight / 2 - circle.width * ratio;
            float right = mWidth / 2 + circle.width;
            float bottom = mHeight / 2 + circle.width * ratio;

            canvas.drawRoundRect(left, top, right, bottom, radius, radius, mPaint);

            // 当圆超出View的宽度后删除
            if (circle.width > mWidth / 2) {
                mRipples.remove(i);
            } else {
                // 计算不透明的数值，这里有个小知识，就是如果不加上double的话，255除以一个任意比它大的数都将是0
                if (mIsAlpha) {
                    double alpha = 255 - circle.width * (255 / ((double) mWidth / 2));
                    circle.alpha = (int) alpha;
                }
                // 修改这个值控制速度
                circle.width += mSpeed;
                circle.height += mSpeed;
            }
        }
        // 里面添加圆
        if (mRipples.size() > 0) {
            // 控制第二个圆出来的间距
            if (mRipples.get(mRipples.size() - 1).width > DensityUtils.dp2px(mDensity)) {
                mRipples.add(new Rectangle(DensityUtils.dp2px(10), DensityUtils.dp2px(10), 255));
            }
        }
        invalidate();
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽度
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            // match_parent
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
            ;
        } else {
            // wrap_content
            mWidth = DensityUtils.dp2px(120);
        }
        // 获取高度
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            mHeight = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            // wrap_content
            mHeight = DensityUtils.dp2px(120);
        }
        setMeasuredDimension((int) mWidth, (int) mHeight);
    }


    private static class Rectangle {
        Rectangle(float width, float height, int alpha) {
            this.width = width;
            this.height = height;
            this.alpha = alpha;
        }

        float width;
        float height;
        int alpha;
    }

}