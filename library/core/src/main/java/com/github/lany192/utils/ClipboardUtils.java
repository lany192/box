package com.github.lany192.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

/**
 * 粘贴板工具
 */
public class ClipboardUtils {

    /**
     * 获取粘贴板上的内容
     */
    public static CharSequence getText(Context context) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager.hasPrimaryClip()) {
            return manager.getPrimaryClip().getItemAt(0).getText();
        } else {
            return "";
        }
    }

    /**
     * 判断粘贴板上是否有内容
     */
    public static boolean isClipboardEmpty(Context context) {
        CharSequence content = getText(context);
        return TextUtils.isEmpty(content);
    }

    /**
     * 设置粘贴板上的内容
     */
    public static void setText(Context context, CharSequence msg) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(null, msg));
    }
}
