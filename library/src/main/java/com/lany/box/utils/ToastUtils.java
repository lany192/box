package com.lany.box.utils;

import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.lany.box.Box;


/**
 * Toast工具类
 */
public class ToastUtils {
    private static final String TAG = "ToastUtil";

    public static void show(int resId) {
        if (resId <= 0) {
            return;
        }
        show(Box.of().getContext().getString(resId));
    }

    public static void show(final CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Box.of().getContext(), text, Toast.LENGTH_SHORT).show();
                XLog.tag(TAG).i("Toast消息:" + text.toString());
            }
        });
    }
}
