package com.github.lany192.box.delegate;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.mvp.BaseContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
 * @author Administrator
 */
public abstract class BaseDelegate<T, M, V extends BaseContract.View> extends ItemDelegate<T> implements LifecycleObserver {
    protected Logger.Builder log = XLog.tag(getClass().getSimpleName());
    private final M model;
    private final V view;
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

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        log.i("onCreate()");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        //log.i("onStart()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        //log.i("onResume()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        //log.i("onPause()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        //log.i("onStop()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        log.i("onDestroy()");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Subscribe
    public void onEvent(NetWorkEvent event) {
        log.i("NetWorkEvent....................");
    }
}