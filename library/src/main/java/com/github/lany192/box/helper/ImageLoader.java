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
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.lany192.box.R;
import com.github.lany192.box.utils.CheckUtils;
import com.github.lany192.box.utils.RoundedCornersTransform;

import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
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

    /**
     * 显示图片
     *
     * @param imageView 显示控件
     * @param model     显示来源，可以是 String/Uri/File/Drawable/Bitmap/Integer
     */
    public void show(ImageView imageView, Object model) {
        load(imageView, model, new RequestOptions(), null);
    }

    /**
     * 显示图片
     */
    public void show(ImageView imageView, Object model, @DrawableRes int errorResId) {
        load(imageView, model, new RequestOptions().error(errorResId), null);
    }

    /**
     * 显示圆角图片，四个角都是圆角
     */
    public void roundCorner(ImageView imageView, Object model, int radiusDp) {
        roundCorner(imageView, model, radiusDp, RoundedCornersTransform.CornerType.ALL);
    }

    /**
     * 指定角显示圆角
     */
    public void roundCorner(ImageView imageView, Object model, int radiusDp, RoundedCornersTransform.CornerType cornerType) {
        load(imageView, model, RequestOptions.bitmapTransform(new RoundedCornersTransform(radiusDp, cornerType)), null);
    }

    /**
     * 显示圆形图片
     */
    public void circle(ImageView imageView, Object model) {
        circle(imageView, model, 0);
    }

    /**
     * 显示图片
     */
    public void circle(ImageView imageView, Object model, @DrawableRes int errorResId) {
        load(imageView, model, RequestOptions.circleCropTransform().error(errorResId), null);
    }

    /**
     * 显示头像，圆形
     */
    public void avatar(ImageView imageView, Object model) {
        load(imageView, model, RequestOptions.circleCropTransform()
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar), null);
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
        options = options.diskCacheStrategy(DiskCacheStrategy.ALL);
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