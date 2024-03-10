package com.github.lany192.utils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class RxBus {
    private final Subject<Object> subject;
    private volatile static RxBus instance = null;

    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    private RxBus() {
        this.subject = PublishSubject.create().toSerialized();
    }

    public void post(Object object) {
        subject.onNext(object);
    }

    public <T> Observable<T> toObservable(Class<T> eventClass) {
        return subject.ofType(eventClass);
    }

}
