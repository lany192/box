package com.github.lany192.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ImageUtils {
    private static Headers headers = Headers.DEFAULT;

    /**
     * 设置防盗链接
     */
    public static void setReferer(String url) {
        headers = () -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Referer", url);
            return headers;
        };
    }

    /**
     * 模糊图片
     */
    public static void blur(ImageView imageView, Object model) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(model)
                .transform(new BlurTransformation())
                .apply(new RequestOptions().placeholder(getRandomColorDrawable(imageView)))
                .into(imageView);
    }

    /**
     * 显示图片
     *
     * @param imageView 显示控件
     * @param model     可以是 String/Uri/File/Drawable/Bitmap/Integer
     */
    public static void show(ImageView imageView, Object model) {
        show(imageView, model, new RequestOptions());
    }

    /**
     * 显示图片
     */
    public static void show(ImageView imageView, Object model, @DrawableRes int errorResId) {
        show(imageView, model, new RequestOptions().error(errorResId));
    }

    /**
     * 显示图片
     */
    public static void show(ImageView imageView, Object model, @DrawableRes int errorResId, int size) {
        show(imageView, model, new RequestOptions().error(errorResId).override(size));
    }

    /**
     * 显示图片
     */
    public static void show(ImageView imageView, Object model, RequestOptions options) {
        load(imageView, model, options);
    }

    /**
     * 通用加载项
     */
    private static void load(ImageView imageView, Object model, RequestOptions options) {
        if (imageView == null) {
            return;
        }
        //判断是否有限定大小
        if (options.getOverrideWidth() == -1 || options.getOverrideHeight() == -1) {
            options = options.override(imageView.getWidth(), imageView.getHeight());
        }
        //判断是否添加占位资源
        if (options.getPlaceholderDrawable() == null && options.getPlaceholderId() == 0) {
            options = options.placeholder(getRandomColorDrawable(imageView));
        }
        //判断是否添加error资源
        if (options.getErrorPlaceholder() == null && options.getErrorId() == 0) {
            options = options.error(getRandomColorDrawable(imageView));
        }
        //判断缓存类型
        if (options.getDiskCacheStrategy() == DiskCacheStrategy.AUTOMATIC) {
            options = options.diskCacheStrategy(DiskCacheStrategy.ALL);
        }
        if (model instanceof String) {
            String url = (String) model;
            model = FormatUtils.isWebUrl(url) ? new GlideUrl(url, headers) : url;
        }
        Glide.with(imageView.getContext())
                .load(model)
                .apply(options)
                .into(imageView);
    }


    /**
     * 随机颜色
     */
    private static Drawable getRandomColorDrawable(ImageView imageView) {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        int color = Color.argb(20, r, g, b);
        Drawable drawable = new ColorDrawable(color);
        drawable.setBounds(0, 0, imageView.getHeight(), imageView.getWidth());
        return drawable;
    }

    /**
     * 高斯模糊
     */
    public static Bitmap blur(Context context, Bitmap bitmap, float radius, float scale) {
        // 计算图片缩小后的长宽
        int width = Math.round(bitmap.getWidth() * scale);
        int height = Math.round(bitmap.getHeight() * scale);

        // 将缩小后的图片做为预渲染的图片
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

        // 创建一张渲染后的输出图片
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(radius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);
        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);
        bitmap.recycle();
        return outputBitmap;
    }
}