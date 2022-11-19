package com.github.lany192.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.helper.DragHelper;

/**
 * 优化ViewPager/ViewPager2和RelativeLayout的子类控件滑动冲突
 */
public class FixDragRelativeLayout extends RelativeLayout {
    private DragHelper dragHelper;

    public FixDragRelativeLayout(@NonNull Context context) {
        super(context);
        dragHelper = new DragHelper(this);
    }

    public FixDragRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        dragHelper = new DragHelper(this);
    }

    public FixDragRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
