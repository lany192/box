package com.github.lany192.box.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.box.utils.DragHelper;

/**
 * 优化ViewPager/ViewPager2和LinearLayout的子类控件滑动冲突
 */
public class FixDragFrameLayout extends FrameLayout {
    private final DragHelper dragHelper;

    public FixDragFrameLayout(@NonNull Context context) {
        super(context);
        dragHelper = new DragHelper(this);
    }

    public FixDragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        dragHelper = new DragHelper(this);
    }

    public FixDragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dragHelper = new DragHelper(this);
    }

    /**
     * 是否直接剥夺父view 对touch 事件的处理权
     */
    public void setDisallow(boolean disallow) {
        dragHelper.setDisallow(disallow);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        dragHelper.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}
