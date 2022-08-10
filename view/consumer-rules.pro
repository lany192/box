#ViewBinding混淆----------------------------------------------
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
  public static ** inflate(...);
  public static ** bind(***);
}

-keep class com.github.lany192.view.BindingView.** { *; }