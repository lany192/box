package com.lany192.box.sample.lancet;

import com.github.lany192.log.XLog;
import com.knightboost.lancet.api.Scope;
import com.knightboost.lancet.api.annotations.Group;
import com.knightboost.lancet.api.annotations.ReplaceInvoke;
import com.knightboost.lancet.api.annotations.TargetClass;
import com.knightboost.lancet.api.annotations.TargetMethod;
import com.knightboost.lancet.api.annotations.Weaver;

@Weaver
@Group("replaceInvokeTest")
public class ReplaceInvokeTest {

    @ReplaceInvoke()
    @TargetClass(value = "com.lany192.box.sample.lancet.Hello", scope = Scope.SELF)
    @TargetMethod(methodName = "printMessage")
    public static void printMessage(Hello a, String msg) {
        msg = msg + "替换";
        XLog.tag("lancet测试").i("我已经不是原始数据了:" + msg);
    }
}