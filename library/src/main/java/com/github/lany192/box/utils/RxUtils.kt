package com.github.lany192.box.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

object RxUtils {

    fun <T> compose(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable: Observable<T> ->
            observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
        }
    }
}