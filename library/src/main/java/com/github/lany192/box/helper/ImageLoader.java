package com.github.lany192.box.helper;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
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

import java.io.File;
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

    public void showAvatar(ImageView imageView, String url) {
        if (imageView == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .override(imageView.getWidth(), imageView.getHeight())
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(new GlideUrl(url, headers))
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, String url) {
        if (imageView == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        RequestBuilder<Drawable> builder;
        if (!TextUtils.isEmpty(url) && CheckUtils.isWebUrl(url)) {
            builder = Glide.with(imageView.getContext()).load(new GlideUrl(url, headers));
        } else {
            builder = Glide.with(imageView.getContext()).load(url);
        }
        builder.apply(options).into(imageView);
    }

    public void show(ImageView imageView, Uri uri) {
        if (imageView == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, File file) {
        if (imageView == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(file)
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, Drawable drawable) {
        if (imageView == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(drawable)
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, Bitmap bitmap) {
        if (imageView == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(bitmap)
                .apply(options)
                .into(imageView);
    }

    public void showFullWidth(ImageView imageView, String url) {
        if (imageView == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        RequestBuilder<Drawable> builder;
        if (!TextUtils.isEmpty(url) && CheckUtils.isWebUrl(url)) {
            builder = Glide.with(imageView.getContext()).load(new GlideUrl(url, headers));
        } else {
            builder = Glide.with(imageView.getContext()).load(url);
        }
        builder.apply(options).addListener(new RequestListener<Drawable>() {

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
        }).into(imageView);
    }
}
