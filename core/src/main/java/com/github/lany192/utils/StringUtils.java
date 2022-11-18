package com.github.lany192.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

import androidx.core.text.HtmlCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * 转成html格式
     */
    public static Spanned getHtml(String text) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableStringBuilder("");
        }
        return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

    /**
     * 是否是纯数字 -?[0-9]+(\\\\.[0-9]+)?
     */
    public static boolean isNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        //正负整数
        String pattern = "^(0|[1-9][0-9]*|-[1-9][0-9]*)$";
        return Pattern.compile(pattern).matcher(str).matches();
    }

    /**
     * String转成boolean
     */
    public static boolean string2boolean(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.equals("true") || str.equals("1");
        }
        return false;
    }

    /**
     * String转成int
     */
    public static int string2int(String str) {
        return string2int(str, 0);
    }

    /**
     * String转成int
     */
    public static int string2int(String str, int defaultValue) {
        if (!TextUtils.isEmpty(str) && StringUtils.isNumber(str)) {
            try {
                return Integer.parseInt(str);
            } catch (Exception e) {
                LogUtils.e("string2int格式装换异常：" + e.getMessage());
            }
        }
        return defaultValue;
    }

    public static long string2long(String str) {
        return string2long(str, 0);
    }

    /**
     * String转成long
     */
    public static long string2long(String str, long defaultValue) {
        if (!TextUtils.isEmpty(str) && StringUtils.isNumber(str)) {
            try {
                return Long.parseLong(str);
            } catch (Exception e) {
                LogUtils.e("string2long格式装换异常：" + e.getMessage());
            }
        }
        return defaultValue;
    }

    public static boolean isBoolean(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return "false".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str);
    }

    /**
     * 判断字符串是不是double型
     */
    public static boolean isDouble(String str) {
        String regex = "[0-9]+[.]{0,1}[0-9]*[dD]{0,1}";
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
