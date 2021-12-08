package com.github.lany192.box.activity;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.github.lany192.box.network.NetworkHelper;
import com.github.lany192.dialog.LoadingDialog;

public abstract class BaseActivity extends AppCompatActivity {
    protected Logger.Builder log = XLog.tag(getClass().getSimpleName());
    private LoadingDialog loadingDialog;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(NetworkHelper.getInstance());
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

    }
}
