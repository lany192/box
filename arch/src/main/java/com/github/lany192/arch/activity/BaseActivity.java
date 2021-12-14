package com.github.lany192.arch.activity;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.arch.BoxApplication;
import com.github.lany192.arch.R;
import com.github.lany192.arch.network.NetworkHelper;
import com.github.lany192.arch.viewmodel.LifecycleViewModel;
import com.github.lany192.dialog.LoadingDialog;
import com.gyf.immersionbar.ImmersionBar;

public abstract class BaseActivity extends AppCompatActivity {
    protected Logger.Builder log = XLog.tag(getClass().getSimpleName());
    private LoadingDialog loadingDialog;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(NetworkHelper.getInstance());
    }

    public <T extends LifecycleViewModel> T getViewModel(@NonNull Class<T> modelClass) {
        T viewModel = new ViewModelProvider(this).get(modelClass);
        getLifecycle().addObserver(viewModel);
        return viewModel;
    }

    public <T extends ViewModel> T getAndroidViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider((BoxApplication) getApplicationContext()).get(modelClass);
    }

    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.loading));
    }

    public void showLoadingDialog(CharSequence message) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog();
        }
        loadingDialog.setMessage(message);
        loadingDialog.show(this);
    }

    public void cancelLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.cancel();
            loadingDialog = null;
        }
    }

    @CallSuper
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (immersionBarEnabled()) {
            initImmersionBar();
        }
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        if (immersionBarEnabled()) {
            initImmersionBar();
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

    public void initImmersionBar() {
        ImmersionBar.with(this).transparentStatusBar().init();
    }
}
