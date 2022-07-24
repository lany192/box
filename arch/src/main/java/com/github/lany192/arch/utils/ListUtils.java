package com.github.lany192.arch.utils;

import java.util.List;
import java.util.stream.Collectors;

public class ListUtils {
    /**
     * 判断list是否为空
     */
    public static boolean isEmpty(List<?> lists) {
        return lists == null || lists.size() == 0;
    }

    /**
     * 逗号隔开拼接字符串,结果示例：  1,2,3,4
     */
    public static String list2string(List<Long> list) {
        if (isEmpty(list)) {
            return "";
        }
        List<String> items = list.stream().map(String::valueOf).collect(Collectors.toList());
        return join(items);
    }

    /**
     * 逗号隔开拼接字符串,结果示例：  a,b,c,d
     */
    public static String join(List<String> list) {
        if (isEmpty(list)) {
            return "";
        }
        return String.join(",", list);
    }
}
