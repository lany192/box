package com.github.lany192.box.utils;

import android.util.Log;

import com.github.lany192.box.Box;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * luban图片压缩封装
 */
public class PicCompressor {
    private final String TAG = getClass().getSimpleName();
    private List<String> paths;
    private Consumer<List<String>> consumer;

    public PicCompressor(String path, Consumer<List<String>> consumer) {
        this.paths = new ArrayList<>();
        this.paths.add(path);
        this.consumer = consumer;
    }

    public PicCompressor(List<String> paths, Consumer<List<String>> consumer) {
        this.paths = paths;
        this.consumer = consumer;
    }

    public void start() {
        Disposable disposable = Flowable.just(paths)
                .observeOn(Schedulers.io())
                .map(this::compress)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                .subscribe(consumer);
        new CompositeDisposable().add(disposable);
    }

    private List<String> compress(List<String> photosPaths) throws IOException {
        List<File> files = Luban.with(Box.of().getContext())
                .setTargetDir(Box.of().getContext().getCacheDir().getPath())
                .load(photosPaths)
                .ignoreBy(100)//小于100kb不压缩
                .get();
        List<String> paths = new ArrayList<>();
        for (File item : files) {
            paths.add(item.getPath());
        }
        return paths;
    }
}
