package com.github.lany192.box.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.github.lany192.dialog.LoadingDialog;

public class BasicFragment extends Fragment {
    protected final String TAG = this.getClass().getName();
    protected Logger.Builder log = XLog.tag(TAG);
    private LoadingDialog loadingDialog;
    /**
     * 是否执行过懒加载
     */
    private boolean lazyLoaded;

    @Override
    public void onResume() {
        super.onResume();
        if (!lazyLoaded) {
            lazyLoaded = true;
            onLazyLoad();
        }
    }

    /**
     * 如果需要懒加载，逻辑写在这里,只被调用一次
     */
    protected void onLazyLoad() {
        log.i("懒加载...");
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

    protected <T extends ViewModel> T getViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider(this).get(modelClass);
    }

    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider(requireActivity()).get(modelClass);
    }
}
