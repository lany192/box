package com.github.lany192.blurview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.google.android.renderscript.Toolkit;

public class RenderEffectBlur implements BlurAlgorithm {

    public RenderEffectBlur() {
    }

    @Override
    public Bitmap blur(@NonNull Bitmap bitmap, float blurRadius) {
        return Toolkit.INSTANCE.blur(bitmap, (int) blurRadius);
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean canModifyBitmap() {
        return true;
    }

    @NonNull
    @Override
    public Bitmap.Config getSupportedBitmapConfig() {
        return Bitmap.Config.ARGB_8888;
    }

    @Override
    public float scaleFactor() {
        return BlurController.DEFAULT_SCALE_FACTOR;
    }

    @Override
    public void render(@NonNull Canvas canvas, @NonNull Bitmap bitmap) {
        canvas.drawBitmap(bitmap, 0f, 0f, new Paint(Paint.FILTER_BITMAP_FLAG));
    }
}
