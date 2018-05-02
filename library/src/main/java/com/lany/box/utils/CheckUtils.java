package com.lany.box.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {
    /**
     * 验证手机格式
     */
    public static boolean checkPhone(String mobiles) {
        String telRegex = "[1][345678]\\d{9}";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
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
