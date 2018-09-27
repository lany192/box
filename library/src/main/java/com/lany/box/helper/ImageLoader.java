package com.lany.box.helper;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lany.box.R;

import java.io.File;

public class ImageLoader {
    private volatile static ImageLoader instance;

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

    public void showAvatar(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .override(imageView.getWidth(), imageView.getHeight())
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, Uri uri) {
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
}
