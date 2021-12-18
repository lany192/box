package com.github.lany192.arch.fragment;

import android.content.res.Configuration;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.arch.BoxApplication;
import com.github.lany192.arch.R;
import com.github.lany192.arch.viewmodel.LifecycleViewModel;
import com.github.lany192.dialog.LoadingDialog;

public abstract class BasicFragment extends Fragment {
    protected Logger.Builder log = XLog.tag(getClass().getName());
    private LoadingDialog loadingDialog;

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

    public <T extends ViewModel> T getFragmentViewModel(@NonNull Class<T> modelClass) {
        T viewModel = new ViewModelProvider(this).get(modelClass);
        if (viewModel instanceof LifecycleObserver) {
            getLifecycle().addObserver((LifecycleObserver) viewModel);
        }
        return viewModel;
    }

    public <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        T viewModel = new ViewModelProvider(requireActivity()).get(modelClass);
        if (viewModel instanceof LifecycleObserver) {
            getLifecycle().addObserver((LifecycleObserver) viewModel);
        }
        return viewModel;
    }

    public <T extends ViewModel> T getAndroidViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider((BoxApplication) requireActivity().getApplicationContext()).get(modelClass);
    }
}
