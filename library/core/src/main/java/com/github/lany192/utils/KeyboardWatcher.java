package com.github.lany192.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;

/**
 * 检测键盘弹出与收起，计算键盘当前高度
 */
public final class KeyboardWatcher extends PopupWindow implements OnGlobalLayoutListener {
    //PopupWindow的布局视图
    private final View rootView;
    //键盘监听器回调
    private final OnKeyboardListener listener;
    //未弹出输入法时的可见高度
    private int windowVisibleDisplayHeight;
    //当前键盘高度
    private int keyboardHeight;

    public KeyboardWatcher(Activity activity, OnKeyboardListener listener) {
        super(activity);
        this.listener = listener;
        rootView = new View(activity);
        setContentView(rootView);
        // 监听PopupWindow Layout变化
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        setBackgroundDrawable(new ColorDrawable(0));

        // 设置宽度为0，高度为全屏,不显示
        setWidth(0);
        setHeight(LayoutParams.MATCH_PARENT);

        // 设置键盘弹出方式
        setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        if (!isShowing()) {
            final View view = activity.getWindow().getDecorView();
            // 延迟加载PopupWindow，如果不加延迟就会报错
            view.post(() -> showAtLocation(view, Gravity.NO_GRAVITY, 0, 0));
        }
    }

    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);
        if (rect.bottom > windowVisibleDisplayHeight) {
            windowVisibleDisplayHeight = rect.bottom;
        }
        // 两者的差值就是键盘的高度
        int height = windowVisibleDisplayHeight - rect.bottom;
        //键盘高度变化才回调
        if (keyboardHeight != height) {
            keyboardHeight = height;
            if (listener != null) {
                listener.onChanged(height > 0, height);
            }
        }
    }

    public interface OnKeyboardListener {
        void onChanged(boolean showKeyboard, int height);
    }
}

