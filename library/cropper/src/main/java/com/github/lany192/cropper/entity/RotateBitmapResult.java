package com.github.lany192.cropper.entity;

import android.graphics.Bitmap;


public class RotateBitmapResult {
    /**
     * The loaded bitmap
     */
    private Bitmap bitmap;

    /**
     * The degrees the image was rotated
     */
    private int degrees;

    public RotateBitmapResult(Bitmap bitmap, int degrees) {
        this.bitmap = bitmap;
        this.degrees = degrees;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getDegrees() {
        return degrees;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }
}
