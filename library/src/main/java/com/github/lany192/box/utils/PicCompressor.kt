package com.github.lany192.box.utils

import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.box.Box
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import top.zibin.luban.Luban
import java.io.File
import java.io.IOException
import java.util.*
import java.util.stream.Collectors

/**
 * luban图片压缩封装
 */
class PicCompressor {
    var log: Logger.Builder = XLog.tag(javaClass.simpleName)
    private var paths: MutableList<String>
    private var consumer: Consumer<List<String>>

    constructor(
        path: String,
        consumer: Consumer<List<String>>
    ) {
        paths = ArrayList()
        paths.add(path)
        this.consumer = consumer
    }

    constructor(
        paths: MutableList<String>,
        consumer: Consumer<List<String>>
    ) {
        this.paths = paths
        this.consumer = consumer
    }

    fun start(): Disposable {
        return Flowable.just<List<String>>(paths)
            .observeOn(Schedulers.io())
            .map { paths: List<String> -> compress(paths) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable: Throwable ->
                log.e(throwable.message)
            }
            .subscribe(consumer)
    }

    @Throws(IOException::class)
    private fun compress(paths: List<String>): List<String> {
        return Luban.with(Box.of().context)
            .setTargetDir(Box.of().context.cacheDir.path)
            .load(paths)
            .ignoreBy(100) //小于100kb不压缩
            .get()
            .stream()
            .map { obj: File -> obj.path }
            .collect(Collectors.toList())
    }
}