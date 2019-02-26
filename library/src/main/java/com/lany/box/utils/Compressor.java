package com.lany.box.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.annotation.Keep;
import android.support.media.ExifInterface;
import android.util.Log;

import com.lany.box.Box;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 图片压缩器，使用方法：File resultFile = new Compressor(sourcePath).start();
 */
@Keep
public class Compressor {
    private final String TAG = getClass().getSimpleName();
    private ExifInterface mExifInterface;
    private String mSourceImagePath;
    private File mTargetFile;
    private int mSourceWidth;
    private int mSourceHeight;

    public Compressor(String sourcePath) throws Exception {
        this.mExifInterface = new ExifInterface(sourcePath);
        this.mTargetFile = new File(getCacheDirectory(Box.of().getContext()), System.currentTimeMillis() + ".jpg");
        this.mSourceImagePath = sourcePath;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

        BitmapFactory.decodeFile(mSourceImagePath, options);
        this.mSourceWidth = options.outWidth;
        this.mSourceHeight = options.outHeight;
    }

    /**
     * app缓存目录
     */
    private File getCacheDirectory(Context ctx) {
        File cacheDir = ctx.getCacheDir();
        if (cacheDir == null) {
            return Environment.getExternalStorageDirectory();
        }
        return cacheDir;
    }

    private int computeSize() {
        mSourceWidth = mSourceWidth % 2 == 1 ? mSourceWidth + 1 : mSourceWidth;
        mSourceHeight = mSourceHeight % 2 == 1 ? mSourceHeight + 1 : mSourceHeight;

        int longSide = Math.max(mSourceWidth, mSourceHeight);
        int shortSide = Math.min(mSourceWidth, mSourceHeight);

        float scale = ((float) shortSide / longSide);
        if (scale <= 1 && scale > 0.5625) {
            if (longSide < 1664) {
                return 1;
            } else if (longSide < 4990) {
                return 2;
            } else if (longSide > 4990 && longSide < 10240) {
                return 4;
            } else {
                return longSide / 1280 == 0 ? 1 : longSide / 1280;
            }
        } else if (scale <= 0.5625 && scale > 0.5) {
            return longSide / 1280 == 0 ? 1 : longSide / 1280;
        } else {
            return (int) Math.ceil(longSide / (1280.0 / scale));
        }
    }

    private Bitmap rotatingImage(Bitmap bitmap) {
        if (mExifInterface == null)
            return bitmap;
        Matrix matrix = new Matrix();
        int angle = 0;
        int orientation = mExifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                angle = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                angle = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                angle = 270;
                break;
        }
        if (angle != 0) {
            matrix.postRotate(angle);
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        final int maxWidth = 1080;
        final int maxHeight = 1280;
        if (width > maxWidth || height > maxHeight) {
            float scale;
            if (width < height) {
                scale = maxWidth / (float) width;
            } else {
                scale = maxHeight / (float) height;
            }
            matrix.setScale(scale, scale);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public File start() {
        Log.i(TAG, "开始压缩源文件:" + mSourceImagePath + "，大小：" + (mSourceImagePath.length() >> 10) + "kb");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = computeSize();
        Bitmap targetBitmap = BitmapFactory.decodeFile(mSourceImagePath, options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        targetBitmap = rotatingImage(targetBitmap);
        int leastCompressSize = 150;//小于150kb不压缩
        int quality = 100;
        if (needCompress(leastCompressSize, mSourceImagePath)) {
            quality = 60;
        }
        targetBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        if (!targetBitmap.isRecycled()) {
            targetBitmap.recycle();
            targetBitmap = null;
            System.gc();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mTargetFile);
            fos.write(stream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                    stream.close();
                    fos = null;
                    stream = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i(TAG, "压缩结束，压缩后文件:" + mTargetFile.getPath() + "，大小：" + (mTargetFile.length() >> 10) + "kb");
        return mTargetFile;
    }

    /**
     * 判断是否需要压缩
     */
    private boolean needCompress(int leastCompressSize, String path) {
        if (leastCompressSize > 0) {
            File source = new File(path);
            return source.exists() && source.length() > (leastCompressSize << 10);
        }
        return true;
    }
}