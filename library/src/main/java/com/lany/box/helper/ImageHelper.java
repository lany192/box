package com.lany.box.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.lany.box.Box;
import com.lany.box.R;
import com.lany.box.utils.PhoneUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 图片加载工具
 */
public class ImageHelper {
    private volatile static ImageHelper instance;

    public static ImageHelper getInstance() {
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
        initImageLoader();
    }

    private void initImageLoader() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 12;
        int width = PhoneUtils.getDeviceWidth() / 2;
        int height = PhoneUtils.getDeviceHeight() / 2;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(Box.of().getContext())
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
                .build();
        ImageLoader.getInstance().init(config);
    }

    public void show(ImageView imageView, String url) {
        show(imageView, url, null);
    }

    /**
     * 显示圆形图片
     */
    public void showCircle(ImageView imageView, String url) {
        int radius = imageView.getLayoutParams().height / 2;
        show(imageView, url, radius, R.drawable.default_pic, null, true);
    }

    public void showAvatar(ImageView imageView, String url) {
        int radius = imageView.getLayoutParams().height / 2;
        show(imageView, url, radius, R.drawable.default_avatar, null, true);
    }

    public void show(ImageView imageView, String url, boolean isCache) {
        show(imageView, url, 0, R.drawable.default_pic, null, isCache);
    }

    public void show(ImageView imageView, String url, int cornerRadiusPixels) {
        show(imageView, url, cornerRadiusPixels, R.drawable.default_pic, null, true);
    }

    public void show(ImageView imageView, String url, int cornerRadiusPixels, int defaultResId) {
        show(imageView, url, cornerRadiusPixels, defaultResId, null, true);
    }

    public void show(String url, ImageLoadingListener listener) {
        ImageLoader.getInstance().loadImage(url, listener);
    }

    public void show(ImageView imageView, String url, int cornerRadiusPixels, ImageLoadingListener listener) {
        show(imageView, url, cornerRadiusPixels, R.drawable.default_pic, listener, true);
    }

    public void show(ImageView imageView, String url, ImageLoadingListener listener) {
        show(imageView, url, 0, R.drawable.default_pic, listener, true);
    }

    /**
     * 显示图片
     *
     * @param url                图片地址
     * @param imageView          显示控件
     * @param cornerRadiusPixels 圆角大小
     * @param defaultResId       默认图片
     * @param listener           加载监听
     * @param isCache            是否缓存
     */
    public void show(ImageView imageView, String url, int cornerRadiusPixels, int defaultResId, ImageLoadingListener listener, boolean isCache) {
        if (imageView == null) {
            return;
        }
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 4;
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultResId)
                .showImageForEmptyUri(defaultResId)
                .showImageOnFail(defaultResId)
                .cacheInMemory(isCache)
                .cacheOnDisk(isCache)
                .considerExifParams(isCache)
                .decodingOptions(option)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565);
        if (cornerRadiusPixels > 0) {
            builder.displayer(new RoundedBitmapDisplayer(cornerRadiusPixels));
        } else {
            builder.displayer(new SimpleBitmapDisplayer());
        }
        if (!ImageLoader.getInstance().isInited()) {
            initImageLoader();
        }
        if (listener != null) {
            ImageLoader.getInstance().displayImage(url, imageView, builder.build(), listener);
        } else {
            ImageLoader.getInstance().displayImage(url, imageView, builder.build());
        }
    }
}