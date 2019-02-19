package com.lany.box.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lany.box.R;
import com.lany.box.utils.PhoneUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

public class ImageHelper {
    private volatile static ImageHelper instance;

    public static ImageHelper of() {
        if (instance == null) {
            synchronized (ImageHelper.class) {
                if (instance == null) {
                    instance = new ImageHelper();
                }
            }
        }
        return instance;
    }

    private ImageHelper() {

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

    public void show(ImageView imageView, String url, ImageLoadingListener listener) {
        if (imageView == null) {
            return;
        }
        if (!ImageLoader.getInstance().isInited()) {
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int cacheSize = maxMemory / 12;
            int width = PhoneUtils.getDeviceWidth();
            int height = PhoneUtils.getDeviceHeight();
            ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(imageView.getContext())
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .threadPoolSize(5)//设置并发线程数
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.FIFO)
                    .memoryCacheSize(cacheSize)
                    .memoryCache(new WeakMemoryCache())
//                .writeDebugLogs()//打印日志
                    .memoryCacheExtraOptions(width, height) //每个缓存文件的最大长宽
                    .diskCacheExtraOptions(width, height, null)
                    .build());
        }
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 4;
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_pic)
                .showImageForEmptyUri(R.drawable.default_pic)
                .showImageOnFail(R.drawable.default_pic)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .decodingOptions(option)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer());
        ImageLoader.getInstance().displayImage(url, imageView, builder.build(), listener);
    }
}
