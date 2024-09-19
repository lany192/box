#指定代码的压缩级别
-optimizationpasses 5

#禁用代码优化。代码优化是指通过内联方法、简化代码、删除无用（死）代码等操作来提高应用程序的运行效率。使用 -dontoptimize 选项时，ProGuard 或 R8 将不执行这些优化操作。
#这可能会导致应用程序性能略有降低，但构建速度可能会略有提高。在大多数情况下，我们建议允许 ProGuard 或 R8 进行代码优化以提高性能。
-dontoptimize
#禁止代码缩减。代码缩减是指从最终 APK 中移除未使用的类、字段、方法和属性。
#使用 -dontshrink 选项时，这些未使用的代码不会被移除。这会导致 APK 文件变大，因为包含未使用的代码。在大多数情况下，我们建议允许 ProGuard 或 R8 进行代码缩减以减小 APK 大小。
-dontshrink

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

 #优化  不优化输入的类文件
-dontpreverify

 #预校验
-verbose

 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保护注解
-keepattributes *Annotation*,Exceptions,InnerClasses,Signature,Deprecated,Synthetic,EnclosingMethod
-keep public class com.m4399.framework.rxbus.annotation.*

-keepattributes Signature
-keepattributes JavascriptInterface

# 保留行号
-keepattributes SourceFile,LineNumberTable
# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keep class kotlin.**{*;}
-keep class kotlinx.**{*;}

 # 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# target API 低于 Android API 27
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

#gson
-keep class com.google.gson.** {*;}

#retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keep class **$Properties

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}

#所有entity包名后的内容全部不混淆
-keep class **.entity.**{*;}
#keep all the class names suffix with entity
-keep class **.*Entity*{*;}
#包含@SerializedName注释的类不混淆
-keep class **{
@com.google.gson.annotations.SerializedName <fields>;
}

-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable


#@Keep注解混淆----------------------------------------------
-dontwarn androidx.annotation.Keep
-keepattributes *Annotation*
-keep @androidx.annotation.Keep class *
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}
-keep @androidx.annotation.Keep class **{
@androidx.annotation.Keep <fields>;
@androidx.annotation.Keep <methods>;
}

#ViewBinding混淆----------------------------------------------
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
  public static ** inflate(...);
  public static ** bind(***);
}
#arouter混淆----------------------------------------------
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
-keep class * implements com.alibaba.android.arouter.facade.template.IProvider
#----------------------------------------------
-dontwarn dalvik.system.VMStack
-dontwarn javax.lang.model.element.Element
# tencent x5----------------------------------------------
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
-keep class com.tencent.smtt.** { *;}
-keep class com.tencent.tbs.** { *;}
#----------------------------------------------











-dontwarn coil.compose.**
-dontwarn com.amazonaws.**
-dontwarn io.netty.**
-dontwarn io.reactivex.**
-dontwarn io.smallrye.**
-dontwarn java.beans.**
-dontwarn java.lang.reflect.AnnotatedType
-dontwarn javax.xml.**
-dontwarn jdk.jfr.**
-dontwarn joptsimple.**
-dontwarn kotlinx.coroutines.**
-dontwarn org.apache.**
-dontwarn org.aspectj.**
-dontwarn org.jaxen.**
-dontwarn org.slf4j.**
-dontwarn reactor.**
-dontwarn rx.**