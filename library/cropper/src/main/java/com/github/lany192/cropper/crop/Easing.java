package com.github.lany192.cropper.crop;

public interface Easing {
    double easeOut(double var1, double var3, double var5, double var7);

    double easeIn(double var1, double var3, double var5, double var7);

    double easeInOut(double var1, double var3, double var5, double var7);
}