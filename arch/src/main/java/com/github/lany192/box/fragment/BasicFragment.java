package com.github.lany192.box.fragment;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.github.lany192.dialog.LoadingDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;

public abstract class BasicFragment extends ImmersionFragment {
    protected Logger.Builder log = XLog.tag(getClass().getName());
    private LoadingDialog loadingDialog;
//    private StateLayout stateLayout;
//
//    @CallSuper
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        stateLayout = new StateLayout(getContext());
//        stateLayout.setLayoutParams(new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//        stateLayout.setOnRetryListener(this::onRetry);
//        stateLayout.addView(getContentView( inflater,  container,  savedInstanceState));
//        return stateLayout;
//    }
//
//    public abstract View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
//
//    public void setRootView(View view) {
//        stateLayout.addView(view);
//    }
//
//    public void onRetry() {
//        log.i("点击重试");
//    }
//
//    public void showEmpty() {
//        stateLayout.showEmpty();
//    }
//
//    public void showEmpty(String msg) {
//        stateLayout.showEmpty(msg);
//    }
//
//    public void showContent() {
//        stateLayout.showContent();
//    }
//
//    public void showNoWifi() {
//        stateLayout.showNetwork();
//    }
//
//    public void showError() {
//        stateLayout.showError();
//    }
//
//    public void showError(String msg) {
//        stateLayout.showError(msg);
//    }
//
//    public void showLoading() {
//        stateLayout.showLoading();
//    }

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

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).autoDarkModeEnable(true).init();
    }
}
