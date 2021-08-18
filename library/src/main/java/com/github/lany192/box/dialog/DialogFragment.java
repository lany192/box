package com.github.lany192.box.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.fragment.FragmentContract;
import com.github.lany192.box.utils.DensityUtils;
import com.github.lany192.view.StateLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class DialogFragment extends androidx.fragment.app.DialogFragment
        implements StateLayout.OnRetryListener, FragmentContract.View {
    protected final String TAG = this.getClass().getName();
    protected Logger.Builder log = XLog.tag(TAG);
    private StateLayout stateLayout;

    private boolean canceledOnTouchOutside = true;
    private CompositeDisposable disposable = new CompositeDisposable();
    private boolean isInitLoaded;
    private LoadingDialog loadingDialog;

    protected abstract int getLayoutId();

    protected abstract void init();

    /**
     * 是否为底部弹窗
     */
    protected boolean bottomStyle() {
        return false;
    }

    protected int getDialogHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected int getDialogWidth() {
        if (bottomStyle()) {
            return WindowManager.LayoutParams.MATCH_PARENT;
        } else {
            return DensityUtils.dp2px(300);
        }
    }

    @Override
    public int getTheme() {
        if (bottomStyle()) {
            return R.style.BottomDialogTheme;
        } else {
            return super.getTheme();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(isCancelable());
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        if (!isCancelable()) {
            dialog.setOnKeyListener((dialog1, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
        }
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if (window != null) {
            window.setLayout(getDialogWidth(), getDialogHeight());
            if (bottomStyle()) {
                window.setGravity(Gravity.BOTTOM);
            }
        }
        if (!isInitLoaded) {
            isInitLoaded = true;
            init();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, true);
        stateLayout = view.findViewById(R.id.id_state_layout);
        if (stateLayout != null) {
            stateLayout.setOnRetryListener(this);
        }
        return view;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return getView().findViewById(id);
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetWorkEvent event) {
        //log.i(" 网络状态发送变化");
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void showEmpty() {
        if (stateLayout != null) {
            stateLayout.showEmpty();
        } else {
            throw new IllegalArgumentException("请在布局文件中配置id为‘id_state_layout’的StateLayout控件");
        }
    }

    @Override
    public void showEmpty(String msg) {
        if (stateLayout != null) {
            stateLayout.showEmpty(msg);
        } else {
            throw new IllegalArgumentException("请在布局文件中配置id为‘id_state_layout’的StateLayout控件");
        }
    }

    @Override
    public void showContent() {
        if (stateLayout != null) {
            stateLayout.showContent();
        } else {
            throw new IllegalArgumentException("请在布局文件中配置id为‘id_state_layout’的StateLayout控件");
        }
    }

    @Override
    public void showNoWifi() {
        if (stateLayout != null) {
            stateLayout.showNetwork();
        } else {
            throw new IllegalArgumentException("请在布局文件中配置id为‘id_state_layout’的StateLayout控件");
        }
    }

    @Override
    public void showError() {
        if (stateLayout != null) {
            stateLayout.showError();
        } else {
            throw new IllegalArgumentException("请在布局文件中配置id为‘id_state_layout’的StateLayout控件");
        }
    }

    @Override
    public void showError(String msg) {
        if (stateLayout != null) {
            stateLayout.showError(msg);
        } else {
            throw new IllegalArgumentException("请在布局文件中配置id为‘id_state_layout’的StateLayout控件");
        }
    }

    @Override
    public void showLoading() {
        if (stateLayout != null) {
            stateLayout.showLoading();
        } else {
            throw new IllegalArgumentException("请在布局文件中配置id为‘id_state_layout’的StateLayout控件");
        }
    }

    protected void manageDisposable(Disposable disposable) {
        this.disposable.add(disposable);
    }

    @Override
    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.loading));
    }

    @Override
    public void showLoadingDialog(CharSequence message) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog();
        }
        loadingDialog.setMessage(message);
        if (!loadingDialog.isAdded()) {
            loadingDialog.cancel();
        }
        loadingDialog.show(getParentFragmentManager(), TAG);
    }

    @Override
    public void cancelLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isAdded()) {
            loadingDialog.cancel();
            loadingDialog = null;
        }
    }

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        if (isAdded()) {
            manager.beginTransaction().remove(this).commitAllowingStateLoss();
        }
        if (!manager.isStateSaved() && !manager.isDestroyed()) {
            super.show(manager, tag);
        } else {
            log.i("对话框忽略......");
        }
    }

    public void show(@NonNull Context context) {
        FragmentActivity activity = context2activity(context);
        if (activity != null) {
            show(activity);
        } else {
            log.e("没有context，不能调起对话框");
        }
    }

    public void show(@NonNull Fragment fragment) {
        show(fragment.requireActivity());
    }

    public void show(@NonNull FragmentActivity activity) {
        show(activity.getSupportFragmentManager(), TAG);
    }

    public void cancel() {
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().cancel();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(this);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private FragmentActivity context2activity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof FragmentActivity) {
            return (FragmentActivity) context;
        } else if (context instanceof ContextWrapper) {
            return context2activity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }
}