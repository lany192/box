package com.github.lany192.helper;

import android.view.MotionEvent;
import android.view.View;

/**
 * 处理ViewPager2和Pager左右上下滑动事件的关系
 */
public class DragHelper {
    private int startX, startY;
    private final View layout;
    /**
     * 是否直接剥夺父view 对touch 事件的处理权
     */
    private boolean disallow;

    public DragHelper(View layout) {
        this.layout = layout;
    }

    /**
     * 是否直接剥夺父view 对touch 事件的处理权
     */
    public void setDisallow(boolean disallow) {
        this.disallow = disallow;
    }

    public void dispatchTouchEvent(MotionEvent ev) {
        if (disallow) {
            //剥夺父view 对touch 事件的处理权
            layout.getParent().requestDisallowInterceptTouchEvent(true);
        } else {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = (int) ev.getX();
                    startY = (int) ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int endX = (int) ev.getX();
                    int endY = (int) ev.getY();
                    int disX = Math.abs(endX - startX);
                    int disY = Math.abs(endY - startY);
                    if (disX > disY) {
                        layout.getParent().requestDisallowInterceptTouchEvent(layout.canScrollHorizontally(startX - endX));
                    } else {
                        //剥夺父view 对touch 事件的处理权
                        //layout.getParent().requestDisallowInterceptTouchEvent(!layout.canScrollVertically(startY - endY));
                        layout.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    //不剥夺父view 对touch 事件的处理权
                    layout.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
        }
    }
}
