package com.lany.box.utils;

import android.text.TextUtils;

public class StringUtils {

    public static CharSequence checkNoNull(CharSequence str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }
}
