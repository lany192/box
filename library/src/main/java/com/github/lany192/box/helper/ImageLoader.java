package com.github.lany192.box.helper;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.github.lany192.box.utils.CheckUtils;
import com.github.lany192.box.utils.RoundedCornersTransform;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class ImageLoader {
    private volatile static ImageLoader instance;
    private Headers headers = Headers.DEFAULT;

    private ImageLoader() {
    }

    public static ImageLoader get() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    /**
     * 设置防盗链接
     */
    public void setReferer(String url) {
        this.headers = () -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Referer", url);
            return headers;
        };
    }

    public void showOneTimeGif(ImageView imageView, int resId) {
        Glide.with(imageView.getContext())
                .asGif()
                .load(resId)
                .addListener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        resource.setLoopCount(1);
                        return false;
                    }
                })
                .into(imageView);
    }

    /**
     * 显示图片
     *
     * @param imageView 显示控件
     * @param model     可以是 String/Uri/File/Drawable/Bitmap/Integer
     */
    public void show(ImageView imageView, Object model) {
        show(imageView, model, new RequestOptions());
    }

    /**
     * 显示图片
     */
    public void show(ImageView imageView, Object model, @DrawableRes int errorResId) {
        show(imageView, model, new RequestOptions().error(errorResId));
    }

    /**
     * 显示图片
     */
    public void show(ImageView imageView, Object model, @DrawableRes int errorResId, int size) {
        show(imageView, model, new RequestOptions().error(errorResId).override(size));
    }

    /**
     * 指定角显示圆角
     */
    public void show(ImageView imageView, Object model, RoundedCornersTransform.CornerType cornerType, int radiusDp) {
        show(imageView, model, RequestOptions.bitmapTransform(new RoundedCornersTransform(radiusDp, cornerType)));
    }

    /**
     * 显示圆形图片
     */
    public void circle(ImageView imageView, Object model) {
        show(imageView, model, RequestOptions.circleCropTransform());
    }

    /**
     * 显示图片
     */
    public void circle(ImageView imageView, Object model, @DrawableRes int errorResId) {
        show(imageView, model, RequestOptions.circleCropTransform().error(errorResId));
    }

    /**
     * 显示图片
     */
    public void circle(ImageView imageView, Object model, RequestOptions options) {
        show(imageView, model, options.circleCrop());
    }

    /**
     * 显示图片
     */
    public void show(ImageView imageView, Object model, RequestOptions options) {
        load(imageView, model, options, false);
    }

    /**
     * 显示图片原始比例，宽顶满显示控件
     */
    public void show(ImageView imageView, Object model, boolean fullWidth) {
        load(imageView, model, new RequestOptions(), fullWidth);
    }

    /**
     * 通用加载项
     */
    private void load(ImageView imageView, Object model, RequestOptions options, boolean fullWidth) {
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
            model = CheckUtils.isWebUrl(url) ? new GlideUrl(url, headers) : url;
        }
        RequestBuilder<Drawable> builder = Glide.with(imageView.getContext())
                .load(model)
                .apply(options);
        //是否原图比例显示
        if (fullWidth) {
            builder.into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.getLayoutParams().height = imageView.getWidth() * resource.getIntrinsicHeight() / resource.getIntrinsicWidth();
                    imageView.setImageDrawable(resource);
                    imageView.requestLayout();
                }
            });
        } else {
            builder.into(imageView);
        }
    }

    /**
     * 随机颜色
     */
    private Drawable getRandomColorDrawable(ImageView imageView) {
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