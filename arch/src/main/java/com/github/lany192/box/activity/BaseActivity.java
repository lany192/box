package com.github.lany192.box.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.github.lany192.box.network.NetworkHelper;
import com.github.lany192.dialog.LoadingDialog;
import com.github.lany192.view.StateLayout;

public abstract class BaseActivity extends AppCompatActivity {
    protected Logger.Builder log = XLog.tag(getClass().getSimpleName());
    private LoadingDialog loadingDialog;
    private StateLayout stateLayout;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        getLifecycle().addObserver(NetworkHelper.getInstance());
    }

    private View getContentView() {
        stateLayout = new StateLayout(this);
        stateLayout.setLayoutParams(new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        stateLayout.setOnRetryListener(this::onRetry);
        return stateLayout;
    }

    public void setRootView(View view) {
        stateLayout.addView(view);
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

    public void onRetry() {
        log.i("点击重试");
    }

    public void showEmpty() {
        stateLayout.showEmpty();
    }

    public void showEmpty(String msg) {
        stateLayout.showEmpty(msg);
    }

    public void showContent() {
        stateLayout.showContent();
    }

    public void showNoWifi() {
        stateLayout.showNetwork();
    }

    public void showError() {
        stateLayout.showError();
    }

    public void showError(String msg) {
        stateLayout.showError(msg);
    }

    public void showLoading() {
        stateLayout.showLoading();
    }
}
