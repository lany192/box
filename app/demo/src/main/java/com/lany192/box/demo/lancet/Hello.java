package com.lany192.box.demo.lancet;

import com.github.lany192.log.XLog;

public class Hello {

    public void printMessage(String message) {
        XLog.tag("lancet测试").i("我是原始数据:" + message);
    }
}
