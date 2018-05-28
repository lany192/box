package com.lany.box.divider;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;

    public ItemDecoration() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        //left, top, right, bottom
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int itemPosition = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewLayoutPosition();
            Divider divider = getDivider(itemPosition);
            if (divider == null) {
                divider = new Divider.Builder().build();
            }
            if (divider.getLeft().isExist()) {
                int lineWidth = divider.getLeft().getWidth();
                int startPadding = divider.getLeft().getStartPadding();
                int endPadding = divider.getLeft().getEndPadding();
                drawChildLeftVertical(child, canvas, divider.getLeft().getColor(), lineWidth, startPadding, endPadding);
            }
            if (divider.getTop().isExist()) {
                int lineWidth = divider.getTop().getWidth();
                int startPadding = divider.getTop().getStartPadding();
                int endPadding = divider.getTop().getEndPadding();
                drawChildTopHorizontal(child, canvas, divider.getTop().getColor(), lineWidth, startPadding, endPadding);
            }
            if (divider.getRight().isExist()) {
                int lineWidth = divider.getRight().getWidth();
                int startPadding = divider.getRight().getStartPadding();
                int endPadding = divider.getRight().getEndPadding();
                drawChildRightVertical(child, canvas, divider.getRight().getColor(), lineWidth, startPadding, endPadding);
            }
            if (divider.getBottom().isExist()) {
                int lineWidth = divider.getBottom().getWidth();
                int startPadding = divider.getBottom().getStartPadding();
                int endPadding = divider.getBottom().getEndPadding();
                drawChildBottomHorizontal(child, canvas, divider.getBottom().getColor(), lineWidth, startPadding, endPadding);
            }
        }
    }

    private void drawChildBottomHorizontal(View child, Canvas canvas, @ColorInt int color, int lineWidth, int startPadding, int endPadding) {
        int leftPadding = 0;
        int rightPadding = 0;

        if (startPadding <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            leftPadding = -lineWidth;
        } else {
            leftPadding = startPadding;
        }

        if (endPadding <= 0) {
            rightPadding = lineWidth;
        } else {
            rightPadding = -endPadding;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        float left = child.getLeft() - params.leftMargin + leftPadding;
        float right = child.getRight() + params.rightMargin + rightPadding;
        float top = child.getBottom() + params.bottomMargin;
        float bottom = top + lineWidth;
        mPaint.setColor(color);
        canvas.drawRect(left, top, right, bottom, mPaint);

    }

    private void drawChildTopHorizontal(View child, Canvas canvas, @ColorInt int color, int lineWidth, int startPadding, int endPadding) {
        int leftPadding = 0;
        int rightPadding = 0;

        if (startPadding <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            leftPadding = -lineWidth;
        } else {
            leftPadding = startPadding;
        }
        if (endPadding <= 0) {
            rightPadding = lineWidth;
        } else {
            rightPadding = -endPadding;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        float left = child.getLeft() - params.leftMargin + leftPadding;
        float right = child.getRight() + params.rightMargin + rightPadding;
        float bottom = child.getTop() - params.topMargin;
        float top = bottom - lineWidth;
        mPaint.setColor(color);

        canvas.drawRect(left, top, right, bottom, mPaint);

    }

    private void drawChildLeftVertical(View child, Canvas canvas, @ColorInt int color, int lineWidth, int startPadding, int endPadding) {
        int topPadding = 0;
        int bottomPadding = 0;

        if (startPadding <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            topPadding = -lineWidth;
        } else {
            topPadding = startPadding;
        }
        if (endPadding <= 0) {
            bottomPadding = lineWidth;
        } else {
            bottomPadding = -endPadding;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        float top = child.getTop() - params.topMargin + topPadding;
        float bottom = child.getBottom() + params.bottomMargin + bottomPadding;
        float right = child.getLeft() - params.leftMargin;
        float left = right - lineWidth;
        mPaint.setColor(color);

        canvas.drawRect(left, top, right, bottom, mPaint);

    }

    private void drawChildRightVertical(View child, Canvas canvas, @ColorInt int color, int lineWidth, int startPadding, int endPadding) {
        int topPadding = 0;
        int bottomPadding = 0;

        if (startPadding <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            topPadding = -lineWidth;
        } else {
            topPadding = startPadding;
        }
        if (endPadding <= 0) {
            bottomPadding = lineWidth;
        } else {
            bottomPadding = -endPadding;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        float top = child.getTop() - params.topMargin + topPadding;
        float bottom = child.getBottom() + params.bottomMargin + bottomPadding;
        float left = child.getRight() + params.rightMargin;
        float right = left + lineWidth;
        mPaint.setColor(color);

        canvas.drawRect(left, top, right, bottom, mPaint);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //outRect 看源码可知这里只是把Rect类型的outRect作为一个封装了left,right,top,bottom的数据结构,
        //作为传递left,right,top,bottom的偏移值来用的
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        Divider divider = getDivider(itemPosition);
        if (divider == null) {
            divider = new Divider.Builder().build();
        }
        int left = divider.getLeft().getWidth();
        int top = divider.getTop().getWidth();
        int right = divider.getRight().getWidth();
        int bottom = divider.getBottom().getWidth();
        outRect.set(left, top, right, bottom);
    }

    /**
     * 如果要不同的position有不同的分割线，复写该方法
     *
     * @param position
     * @return Divider
     */
    public Divider getDivider(int position) {
        //这个是默认的效果
        return new Divider.Builder()
                .setBottom(true,  Color.TRANSPARENT, 0.5f, 0, 0)
                .build();
    }
}

















