package com.github.lany192.layout;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

/**
 * 此类用于解决 ViewPager2  嵌套 ViewPager2 或者 RecyclerView 等相互嵌套的冲突问题，
 */
public class FixDragLayout extends FrameLayout {
    private final int touchSlop;
    private float initialX;
    private float initialY;

    public FixDragLayout(Context context) {
        super(context);
        ViewConfiguration configuration = ViewConfiguration.get(this.getContext());
        this.touchSlop = configuration.getScaledTouchSlop();
    }

    public FixDragLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration configuration = ViewConfiguration.get(this.getContext());
        this.touchSlop = configuration.getScaledTouchSlop();
    }

    private ViewPager2 getParentViewPager() {
        ViewParent viewParent = this.getParent();
        if (!(viewParent instanceof View)) {
            viewParent = null;
        }
        View v;
        for (v = (View) viewParent; v != null && !(v instanceof ViewPager2); v = (View) viewParent) {
            viewParent = v.getParent();
            if (!(viewParent instanceof View)) {
                viewParent = null;
            }
        }
        View var2 = v;
        if (!(v instanceof ViewPager2)) {
            var2 = null;
        }

        return (ViewPager2) var2;
    }

    private View getChild() {
        return this.getChildCount() > 0 ? this.getChildAt(0) : null;
    }

    private boolean canChildScroll(int orientation, float delta) {
        int direction = -((int) Math.signum(delta));
        View view;
        boolean var6 = false;
        switch (orientation) {
            case 0:
                view = this.getChild();
                var6 = view != null && view.canScrollHorizontally(direction);
                break;
            case 1:
                view = this.getChild();
                var6 = view != null && view.canScrollVertically(direction);
                break;
            default:
        }
        return var6;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        this.handleInterceptTouchEvent(e);
        return super.onInterceptTouchEvent(e);
    }

    private void handleInterceptTouchEvent(MotionEvent e) {
        ViewPager2 viewPager2 = this.getParentViewPager();
        if (viewPager2 != null) {
            int orientation = viewPager2.getOrientation();
            if (this.canChildScroll(orientation, -1.0F) || this.canChildScroll(orientation, 1.0F)) {
                if (e.getAction() == 0) {
                    this.initialX = e.getX();
                    this.initialY = e.getY();
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                } else if (e.getAction() == 2) {
                    float dx = e.getX() - this.initialX;
                    float dy = e.getY() - this.initialY;
                    boolean isVpHorizontal = orientation == 0;
                    float scaledDx = Math.abs(dx) * (isVpHorizontal ? 0.5F : 1.0F);
                    float scaledDy = Math.abs(dy) * (isVpHorizontal ? 1.0F : 0.5F);
                    if (scaledDx > (float) this.touchSlop || scaledDy > (float) this.touchSlop) {
                        if (isVpHorizontal == scaledDy > scaledDx) {
                            this.getParent().requestDisallowInterceptTouchEvent(false);
                        } else
                            this.getParent().requestDisallowInterceptTouchEvent(this.canChildScroll(orientation, isVpHorizontal ? dx : dy));
                    }
                }
            }
        }
    }
}