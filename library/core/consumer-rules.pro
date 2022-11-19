#@Keep注解混淆----------------------------------------------
-dontwarn androidx.annotation.Keep
-keepattributes *Annotation*
-keep @androidx.annotation.Keep class **{
@androidx.annotation.Keep <fields>;
@androidx.annotation.Keep <methods>;
}