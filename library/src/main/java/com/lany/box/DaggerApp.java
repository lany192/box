package com.lany.box;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.HasContentProviderInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.HasServiceInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * 如果使用了dagger，用这个基类
 */
public abstract class DaggerApp extends BaseApp implements
        HasActivityInjector,
        HasFragmentInjector,
        HasServiceInjector,
        HasBroadcastReceiverInjector,
        HasContentProviderInjector,
        HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;
    @Inject
    DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;
    @Inject
    DispatchingAndroidInjector<Service> serviceInjector;
    @Inject
    DispatchingAndroidInjector<ContentProvider> contentProviderInjector;
    @Inject
    DispatchingAndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector;

    private volatile boolean needToInject = true;

    @Override
    public void onCreate() {
        super.onCreate();
        injectIfNecessary();
    }

    @Override
    public DispatchingAndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    protected abstract AndroidInjector<? extends DaggerApp> applicationInjector();

    private void injectIfNecessary() {
        if (needToInject) {
            synchronized (this) {
                if (needToInject) {
                    @SuppressWarnings("unchecked")
                    AndroidInjector<DaggerApp> applicationInjector =
                            (AndroidInjector<DaggerApp>) applicationInjector();
                    applicationInjector.inject(this);
                    if (needToInject) {
                        throw new IllegalStateException(
                                "The AndroidInjector returned from applicationInjector() did not inject the "
                                        + "DaggerApplication");
                    }
                }
            }
        }
    }

    @Inject
    void setInjected() {
        needToInject = false;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public DispatchingAndroidInjector<Fragment> fragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return broadcastReceiverInjector;
    }

    @Override
    public DispatchingAndroidInjector<Service> serviceInjector() {
        return serviceInjector;
    }

    // injectIfNecessary is called here but not on the other *Injector() methods because it is the
    // only one that should be called (in AndroidInjection.inject(ContentProvider)) before
    // Application.onCreate()
    @Override
    public AndroidInjector<ContentProvider> contentProviderInjector() {
        injectIfNecessary();
        return contentProviderInjector;
    }
}
