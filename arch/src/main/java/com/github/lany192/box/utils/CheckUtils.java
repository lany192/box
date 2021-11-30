package com.github.lany192.box.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式，可参考：https://github.com/klinker24/talon-twitter-holo/blob/master/app/src/main/java/com/klinker/android/twitter/utils/text/Regex.java
 */
public class CheckUtils {
    /**
     * 验证手机格式
     */
    public static boolean checkPhone(String mobiles) {
        String telRegex = "[1][3456789]\\d{9}";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    /**
     * 是否为web链接格式
     */
    public static boolean isWebUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Pattern pattern = Pattern.compile(
                "((?:(http|https|Http|Https):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
                        + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
                        + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
                        + "((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+"   // named host
                        + "(?:"   // plus top level domain
                        + "(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])"
                        + "|(?:biz|b[abdefghijmnorstvwyz])"
                        + "|(?:cat|com|coop|c[acdfghiklmnoruvxyz])"
                        + "|d[ejkmoz]"
                        + "|(?:edu|e[cegrstu])"
                        + "|f[ijkmor]"
                        + "|(?:gov|g[abdefghilmnpqrstuwy])"
                        + "|h[kmnrtu]"
                        + "|(?:info|int|i[delmnoqrst])"
                        + "|(?:jobs|j[emop])"
                        + "|k[eghimnrwyz]"
                        + "|l[abcikrstuvy]"
                        + "|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])"
                        + "|(?:name|net|n[acefgilopruz])"
                        + "|(?:org|om)"
                        + "|(?:pro|p[aefghklmnrstwy])"
                        + "|qa"
                        + "|r[eouw]"
                        + "|s[abcdeghijklmnortuvyz]"
                        + "|(?:tel|travel|t[cdfghjklmnoprtvwz])"
                        + "|u[agkmsyz]"
                        + "|v[aceginu]"
                        + "|w[fs]"
                        + "|y[etu]"
                        + "|z[amw]))"
                        + "|(?:(?:25[0-5]|2[0-4]" // or ip address
                        + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]"
                        + "|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]"
                        + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                        + "|[1-9][0-9]|[0-9])))"
                        + "|\\.\\.\\."
                        + "(?:\\:\\d{1,5})?)" // plus option port number
                        + "(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\#\\~"  // plus option query params
                        + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
                        + "(?:\\b|$)");
        Matcher m = pattern.matcher(url);
        return m.matches();
    }

    /**
     * 是否为email格式
     */
    public static boolean isEmail(String strEmail) {
        if (TextUtils.isEmpty(strEmail)) {
            return false;
        }
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]*@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 是否为姓名格式,真实姓名(2~7个中文或者3~10个英文)
     */
    public static boolean checkName(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        String regx = "(([\u4E00-\u9FA5]{2,7})|([a-zA-Z]{3,10}))";
        Pattern p = Pattern.compile(regx);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 检查密码格式
     */
    public static boolean checkPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6 && password.length() <= 20;
    }
}
