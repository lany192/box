package com.github.lany192.utils;

import java.util.Random;

public class RandomUtils {
    /**
     * 获取指定范围的随机数
     *
     * @param startInclusive 开始范围
     * @param endExclusive   结束范围
     */
    public static int nextInt(final int startInclusive, final int endExclusive) {
        if (startInclusive == endExclusive) {
            return startInclusive;
        }
        return startInclusive + new Random().nextInt(endExclusive - startInclusive);
    }
}
