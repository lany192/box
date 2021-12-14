package com.github.lany192.arch.utils;

import android.util.Log;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * luban图片压缩封装
 */
public class PicCompressor {
    private final String TAG = getClass().getSimpleName();
    private final List<String> paths;
    private int limitSize = 100;

    public PicCompressor(String path) {
        this.paths = new ArrayList<>();
        this.paths.add(path);
    }

    public PicCompressor(List<String> paths) {
        this.paths = paths;
    }

    public void setLimitSize(int limitSize) {
        this.limitSize = limitSize;
    }

    public Disposable start(Consumer<List<String>> consumer) {
        return Flowable.just(paths)
                .observeOn(Schedulers.io())
                .map(this::compress)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                .subscribe(consumer);
    }

    private List<String> compress(List<String> photosPaths) throws IOException {
        List<File> files = Luban.with(ContextUtils.getContext())
                .setTargetDir(ContextUtils.getContext().getCacheDir().getPath())
                .load(photosPaths)
                .ignoreBy(limitSize)//小于100kb不压缩
                .get();
        List<String> paths = new ArrayList<>();
        for (File item : files) {
            paths.add(item.getPath());
        }
        return paths;
    }
}
