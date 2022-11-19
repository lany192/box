package com.github.lany192.cropper.entity;

import android.graphics.Bitmap;

/**
 * Holds bitmap instance and the sample size that the bitmap was loaded/cropped with.
 */

public class BitmapSampled {
    /**
     * The bitmap instance
     */
    private Bitmap bitmap;

    /**
     * The sample size used to lower the size of the bitmap (1,2,4,8,...)
     */
    private int sampleSize;

    public BitmapSampled(Bitmap bitmap, int sampleSize) {
        this.bitmap = bitmap;
        this.sampleSize = sampleSize;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(int sampleSize) {
        this.sampleSize = sampleSize;
    }
}
