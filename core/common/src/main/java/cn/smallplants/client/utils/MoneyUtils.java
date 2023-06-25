package cn.smallplants.client.utils;

import java.math.BigDecimal;


public class MoneyUtils {
    /**
     * 格式化金额（保留两位小数）
     */
    public static String format(float money) {
        return String.valueOf(float2float(money));
    }

    /**
     * 分转元
     */
    public static String fen2yuan(Integer fen) {
        return BigDecimal.valueOf((double) fen / 100.0D).setScale(2, 4).toPlainString();
    }

    /**
     * 分转元
     */
    public static int yuan2fen(float yuan) {
        return (int) (yuan * 100);
    }

    public static float float2float(float money) {
        // 设置位数
        int scale = 2;
        // 表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        int roundingMode = 4;
        BigDecimal bd = new BigDecimal(money);
        bd = bd.setScale(scale, roundingMode);
        return bd.floatValue();
    }
}
