package com.github.lany192.arch.utils;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewUtils {
    public static void applyWindowInsets(View rootView, View toolbar) {
        int toolbarPaddingTop = toolbar.getPaddingTop();
        int toolbarHeight = toolbar.getLayoutParams().height;
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            ViewGroup.LayoutParams lp = toolbar.getLayoutParams();
            if (lp != null && lp.height > 0) {
                lp.height = toolbarHeight + systemBars.top;//增高
            }
            toolbar.setPadding(toolbar.getPaddingLeft(),
                    toolbarPaddingTop + systemBars.top,
                    toolbar.getPaddingRight(), toolbar.getPaddingBottom()
            );
            return insets;
        });
    }

    /**
     * 增加View的paddingTop,增加的值为状态栏高度
     */
    public static void setPaddingSmart(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            int statusBarHeight = PhoneUtils.getStatusBarHeight();
            if (lp != null && lp.height > 0) {
                lp.height += statusBarHeight;//增高
            }
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight,
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    /**
     * 是否显示灰色模式,遇到特殊节日会有设置灰色主题
     */
    public static void setGrayStyle(Activity activity, boolean gray) {
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        if (gray) {
            cm.setSaturation(0.0F);//灰度效果 取值gray（0 - 1）  0是灰色，1取消灰色
        } else {
            cm.setSaturation(1.0F);//灰度效果 取值gray（0 - 1）  0是灰色，1取消灰色
        }
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        activity.getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }

    /**
     * 是否显示灰色模式,遇到特殊节日会有设置灰色主题
     */
    public static void setGrayStyle(View view, boolean gray) {
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        if (gray) {
            cm.setSaturation(0.0F);//灰度效果 取值gray（0 - 1）  0是灰色，1取消灰色
        } else {
            cm.setSaturation(1.0F);//灰度效果 取值gray（0 - 1）  0是灰色，1取消灰色
        }
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        if (view != null) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        }
    }
}
