package com.lany.box.utils;

import android.util.Log;

import com.lany.box.Box;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * luban图片压缩封装
 */
public class Compressor {
    private final String TAG = getClass().getSimpleName();
    private List<String> paths;
    private Consumer<List<String>> consumer;

    public Compressor(String path, Consumer<List<String>> consumer) {
        this.paths = new ArrayList<>();
        this.paths.add(path);
        this.consumer = consumer;
    }

    public Compressor(List<String> paths, Consumer<List<String>> consumer) {
        this.paths = paths;
        this.consumer = consumer;
    }

    public void start() {
        new CompositeDisposable().add(Flowable.just(paths)
                .observeOn(Schedulers.io())
                .map(this::compress)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                .onErrorResumeNext(Flowable.empty())
                .subscribe(consumer));
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
