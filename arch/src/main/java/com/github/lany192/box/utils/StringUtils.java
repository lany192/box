package com.github.lany192.box.utils;

import android.text.TextUtils;

public class StringUtils {

    public static CharSequence checkNoNull(CharSequence str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }
}
