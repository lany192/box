package com.github.lany192.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.core.view.WindowInsetsCompat;

/**
 * 检测键盘弹出与收起，计算键盘当前高度
 */
public class KeyboardWatcher {
    //未弹出输入法时的可见高度
    private int windowVisibleDisplayHeight;
    //当前键盘高度
    private int keyboardHeight;

    public KeyboardWatcher(Activity activity, final KeyboardWatcher.OnKeyboardListener listener) {
        View decorView = activity.getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            decorView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                WindowInsets windowInsets = activity.getWindow().getDecorView().getRootWindowInsets();
                int imeHeight = windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
                int navigationBarHeight = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
                boolean navigationBarVisible = windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars());
                boolean hasNavigationBar = navigationBarVisible && navigationBarHeight > 0;
                boolean floatMode = navigationBarHeight == imeHeight;
                Log.i("KeyboardWatcher", "imeHeight: " + imeHeight + ", navigationBarHeight: "
                        + navigationBarHeight + ", hasNavigationBar: " + hasNavigationBar + ", floatMode: " + floatMode);
                if (keyboardHeight != imeHeight) {
                    keyboardHeight = imeHeight;
                    if (listener != null) {
                        listener.onChanged(imeHeight > 0, keyboardHeight, floatMode);
                    }
                }
            });
        } else {
            PopupWindow popupWindow = new PopupWindow(activity);
            View rootView = new View(activity);
            popupWindow.setContentView(rootView);
            // 监听PopupWindow Layout变化
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);
                if (rect.bottom > windowVisibleDisplayHeight) {
                    windowVisibleDisplayHeight = rect.bottom;
                }
                // 两者的差值就是键盘的高度
                int height = windowVisibleDisplayHeight - rect.bottom;
                Log.i("KeyboardWatcher", "imeHeight: " + height + ", navigationBarHeight: "
                        + rect.bottom + ", height: " + height);
                if (keyboardHeight != height) {
                    keyboardHeight = height;
                    if (listener != null) {
                        listener.onChanged(height > 0, height, false);
                    }
                }
            });
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
            // 设置宽度为0，高度为全屏,不显示
            popupWindow.setWidth(0);
            popupWindow.setHeight(LayoutParams.MATCH_PARENT);
            // 设置键盘弹出方式
            popupWindow.setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_RESIZE | LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

            decorView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(@NonNull View view) {
                    if (!popupWindow.isShowing() && decorView.getWindowToken() != null) {
                        popupWindow.showAtLocation(decorView, Gravity.NO_GRAVITY, 0, 0);
                    }
                }

                @Override
                public void onViewDetachedFromWindow(@NonNull View view) {
                }
            });
        }
    }

    public interface OnKeyboardListener {
        /**
         * 键盘状态监听
         *
         * @param showKeyboard   是否显示键盘
         * @param keyboardHeight 键盘高度
         * @param floatMode      是否是悬浮模式
         */
        void onChanged(boolean showKeyboard, int keyboardHeight, boolean floatMode);
    }
}

