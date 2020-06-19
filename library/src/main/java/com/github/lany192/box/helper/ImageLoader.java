package com.github.lany192.box.helper;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.lany192.box.R;
import com.github.lany192.box.utils.CheckUtils;
import com.github.lany192.box.utils.RoundedCornersTransform;
import com.github.lany192.box.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public final class ImageLoader {
    private volatile static ImageLoader instance;
    private Headers headers = Headers.DEFAULT;

    public static ImageLoader of() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    private ImageLoader() {
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
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
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
     * 显示头像，圆形
     */
    public void avatar(ImageView imageView, Object model) {
        show(imageView, model, RequestOptions.circleCropTransform()
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar));
    }

    /**
     * 显示图片
     */
    public void show(ImageView imageView, Object model, RequestOptions options) {
        load(imageView, model, options, null);
    }

    /**
     * 显示图片原始比例，宽顶满显示控件
     */
    public void showFullWidth(ImageView imageView, Object model) {
        load(imageView, model, new RequestOptions(), new RequestListener<Drawable>() {

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                imageView.setVisibility(View.VISIBLE);
                imageView.getLayoutParams().height = imageView.getWidth() * resource.getIntrinsicHeight() / resource.getIntrinsicWidth();
                imageView.setImageDrawable(resource);
                imageView.requestLayout();
                return true;
            }
        });
    }

    /**
     * 通用加载项
     */
    private void load(ImageView imageView, Object model, RequestOptions options, RequestListener<Drawable> listener) {
        if (imageView == null) {
            return;
        }
        //判断是否有限定大小
        if (options.getOverrideWidth() == -1 || options.getOverrideHeight() == -1) {
            options = options.override(imageView.getWidth(), imageView.getHeight());
        }
        //判断是否添加占位资源
        if (options.getPlaceholderDrawable() == null && options.getPlaceholderId() == 0) {
            options = options.placeholder(R.drawable.default_pic);
        }
        //判断是否添加error资源
        if (options.getErrorPlaceholder() == null && options.getErrorId() == 0) {
            options = options.error(R.drawable.default_pic);
        }
        //判断缓存类型
        if (options.getDiskCacheStrategy() == DiskCacheStrategy.AUTOMATIC) {
            options = options.diskCacheStrategy(DiskCacheStrategy.ALL);
        }
        if (model instanceof String) {
            String url = (String) model;
            Glide.with(imageView.getContext())
                    .load(CheckUtils.isWebUrl(url) ? new GlideUrl(url, headers) : url)
                    .apply(options)
                    .addListener(listener)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(model)
                    .apply(options)
                    .addListener(listener)
                    .into(imageView);
        }
    }
}