package com.github.lany192.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
}