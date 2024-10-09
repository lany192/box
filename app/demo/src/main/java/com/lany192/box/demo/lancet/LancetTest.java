package com.lany192.box.demo.lancet;

import android.util.Log;

public class LancetTest {

    public static void test() {
        Log.i("插桩测试1啊啊", "插桩测试1哈哈哈哈啊");
        Log.e("插桩测试2啊啊", "插桩测试2哈哈哈哈啊");
        new Hello().printMessage("我是一个文案");
    }
}
