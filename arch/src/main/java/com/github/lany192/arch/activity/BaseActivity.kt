package com.github.lany192.arch.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.arch.R;
import com.github.lany192.arch.network.NetworkHelper;
import com.github.lany192.dialog.LoadingDialog;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Logger.Builder log = XLog.tag(getClass().getSimpleName());
    private LoadingDialog loadingDialog;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(NetworkHelper.getInstance());
        //控制屏幕方向
        if (isPortraitScreen()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Void event) {

    }

    /**
     * 自定义屏幕方向，默认竖屏
     * 注意：需要配置 android:screenOrientation="nosensor"
     */
    public boolean isPortraitScreen() {
        return true;
    }

    public <T extends ViewModel> T getViewModel(@NonNull Class<T> modelClass) {
        T viewModel = new ViewModelProvider(this).get(modelClass);
        if (viewModel instanceof LifecycleObserver) {
            getLifecycle().addObserver((LifecycleObserver) viewModel);
        }
        return viewModel;
    }

    public <T extends ViewModel> T getAndroidViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider((ViewModelStoreOwner) getApplicationContext()).get(modelClass);
    }

    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.loading));
    }

    public void showLoadingDialog(CharSequence message) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog();
        }
        loadingDialog.setMessage(message);
        loadingDialog.show();
    }

    public void cancelLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.cancel();
            loadingDialog = null;
        }
    }

    @CallSuper
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (immersionBarEnabled()) {
            initImmersionBar().init();
        }
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        if (immersionBarEnabled()) {
            initImmersionBar().init();
        }
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    public boolean immersionBarEnabled() {
        return true;
    }

    @NonNull
    public ImmersionBar initImmersionBar() {
        return ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .navigationBarColor(R.color.white_bg)
                .navigationBarDarkIcon(true);
    }

    @ColorInt
    public int getColorResId(@ColorRes int id) {
        return ContextCompat.getColor(this, id);
    }
}
