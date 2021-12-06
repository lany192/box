package com.github.lany192.box.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.github.lany192.dialog.LoadingDialog;
import com.github.lany192.view.StateLayout;

public abstract class BasicFragment extends Fragment {
    protected Logger.Builder log = XLog.tag(getClass().getName());
    private LoadingDialog loadingDialog;
    private StateLayout stateLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stateLayout = new StateLayout(getContext());
        stateLayout.setLayoutParams(new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        stateLayout.setOnRetryListener(this::onRetry);
        stateLayout.addView(getContentView( inflater,  container,  savedInstanceState));
        return stateLayout;
    }

    public abstract View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void setRootView(View view) {
        stateLayout.addView(view);
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
}
