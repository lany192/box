package com.lany192.box.sample.lancet;

import com.knightboost.lancet.api.annotations.Weaver;

@Weaver
public class LogProxy {

//    @Proxy()
//    @TargetClass(value = "android.util.Log", scope = Scope.SELF)
//    @TargetMethod(methodName = "i")
//    public static int replaceLogI(String tag, String msg) {
//        msg = msg + "lancet";
//        tag = "被替换后的" + tag;
//        return (int) Origin.call();
//    }
//
//    @ReplaceInvoke(isStatic = true)
//    @TargetClass(value = "android.util.Log", scope = Scope.SELF)
//    @TargetMethod(methodName = "e")
//    public static int replaceLogE(String tag, String msg) {
//        msg = msg + "被替换";
//        tag = "被替换后的" + tag;
//        return Log.e(tag, msg);
//    }
}
