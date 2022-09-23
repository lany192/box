package com.lany192.box.sample;

import com.knightboost.lancet.api.Origin;
import com.knightboost.lancet.api.Scope;
import com.knightboost.lancet.api.annotations.Proxy;
import com.knightboost.lancet.api.annotations.TargetClass;
import com.knightboost.lancet.api.annotations.TargetMethod;
import com.knightboost.lancet.api.annotations.Weaver;

@Weaver
public class LogProxy {

    @Proxy("")
    @TargetClass(value = "android.util.Log", scope = Scope.SELF)
    @TargetMethod(methodName = "i")
    public static int replaceLogI(String tag, String msg) {
        msg = msg + "lancet";
        return (int) Origin.call();
    }
}
