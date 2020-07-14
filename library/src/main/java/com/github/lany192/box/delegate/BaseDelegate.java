package com.github.lany192.box.delegate;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.github.lany192.box.mvp.BaseView;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * 具有请求调用接口能力的代理
 *
 * @param <T> 数据类型
 * @param <M> app请求接口
 */
public abstract class BaseDelegate<T, M, V extends BaseView> extends ItemDelegate<T> implements LifecycleObserver {
    private M model;
    private V view;
    private CompositeDisposable compositeDisposable;

    public BaseDelegate(T data, M model, V view) {
        super(data);
        this.model = model;
        this.view = view;
        view.getLifecycle().addObserver(this);
    }

    public M getModel() {
        return model;
    }

    public V getView() {
        return view;
    }

    protected <T> void request(Observable<T> observable, DisposableObserver<T> observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}