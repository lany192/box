package com.lany192.box.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.log.LogUtils;

public class DemoLayout extends FrameLayout {
    private boolean subclass;
    private boolean parentClass;
    private float initialX;
    private float initialY;

    public DemoLayout(@NonNull Context context) {
        this(context, null);
    }

    public DemoLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DemoLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSubclass(boolean subclass) {
        this.subclass = subclass;
    }

    public void setParentClass(boolean parentClass) {
        this.parentClass = parentClass;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (subclass && parentClass) {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                initialX = e.getX();
                initialY = e.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
            } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
                float dx = e.getX() - initialX;
                float dy = e.getY() - initialY;
                LogUtils.i("滑动测试：dx==" + dx + ",dy==" + dy);
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0) {
                        LogUtils.i("滑动测试:右滑动");
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        LogUtils.i("滑动测试:左滑动");
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                } else {
                    if (dy > 0) {
                        LogUtils.i("滑动测试:下滑动");
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        LogUtils.i("滑动测试:上滑动");
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
            } else if (e.getAction() == MotionEvent.ACTION_UP) {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return super.onInterceptTouchEvent(e);
    }

}