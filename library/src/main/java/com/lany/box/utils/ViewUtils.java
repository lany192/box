package com.lany.box.utils;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {
    /**
     * 增加View的paddingTop,增加的值为状态栏高度
     */
    public static void setPaddingSmart(View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            int statusBarHeight = PhoneUtils.getStatusBarHeight();
            if (lp != null && lp.height > 0) {
                lp.height += statusBarHeight;//增高
            }
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight,
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }
}
