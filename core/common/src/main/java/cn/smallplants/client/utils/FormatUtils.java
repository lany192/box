package cn.smallplants.client.utils;

import java.text.DecimalFormat;


public class FormatUtils {

    public static String money(float money) {
        DecimalFormat df = new DecimalFormat("￥0.00");
        return df.format(money);
    }

    public static String money(double money) {
        DecimalFormat df = new DecimalFormat("￥0.00");
        return df.format(money);
    }

    public static String moneyWithoutUnits(float money) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(money);
    }

    public static String moneyWithoutUnits(double money) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(money);
    }
}
