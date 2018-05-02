package com.lany.box.utils;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

public class ClipboardUtils {

    /**
     * 获取粘贴板上的内容
     */
    public static CharSequence getText(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager manager = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (manager.hasPrimaryClip()) {
                return manager.getPrimaryClip().getItemAt(0).getText();
            } else {
                return null;
            }
        } else {
            android.text.ClipboardManager manager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            return manager.getText();
        }
    }

    /**
     * 判断粘贴板上是否有内容
     */
    public static boolean isClipboardEmpty(Context context) {
        return TextUtils.isEmpty(getText(context));
    }

    /**
     * 设置粘贴板上的内容
     */
    public static void setText(Context context, CharSequence msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager manager = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            manager.setPrimaryClip(ClipData.newPlainText(null, msg));
        } else {
            android.text.ClipboardManager manager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            manager.setText(msg);
        }
    }
}
