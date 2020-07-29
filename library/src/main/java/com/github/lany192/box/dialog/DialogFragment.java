package com.github.lany192.box.dialog;

import android.app.Dialog;
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
import android.widget.RelativeLayout.LayoutParams;

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
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.mvp.BaseView;
import com.github.lany192.box.utils.DensityUtils;
import com.github.lany192.view.StateLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class DialogFragment extends androidx.fragment.app.DialogFragment implements StateLayout.OnRetryListener, BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    protected FragmentActivity self;
    private StateLayout stateLayout;
    private Unbinder unbinder;

    protected boolean canceledOnTouchOutside = true;

    /**
     * 是否执行过懒加载
     */
    private boolean isLazyLoaded;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        this.self = getActivity();
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
        //需要在FragmentStatePagerAdapter构造方法中配置
        if (!isLazyLoaded) {
            isLazyLoaded = true;
            init();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stateLayout = new StateLayout(self);
        stateLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        stateLayout.setOnRetryListener(this);
        stateLayout.addView(inflater.inflate(getLayoutId(), null));
        unbinder = ButterKnife.bind(this, stateLayout);
        return stateLayout;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return stateLayout.findViewById(id);
    }

    @Override
    public void onDestroy() {
        log.i(TAG + " onDestroy()");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (null != unbinder) {
            unbinder.unbind();
        }
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = null;
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
        stateLayout.showEmpty();
    }

    @Override
    public void showEmpty(String msg) {
        stateLayout.showEmpty(msg);
    }

    @Override
    public void showContent() {
        stateLayout.showContent();
    }

    @Override
    public void showNoWifi() {
        stateLayout.showNetwork();
    }

    @Override
    public void showError() {
        stateLayout.showError();
    }

    @Override
    public void showError(String msg) {
        stateLayout.showError(msg);
    }

    @Override
    public void showLoading() {
        stateLayout.showLoading();
    }

    protected void manageDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        if (isAdded()) {
            log.w(TAG, "已经显示，忽略......");
        } else {
            super.show(manager, tag);
        }
    }

    public void show(@NonNull Fragment fragment) {
        show(fragment.requireActivity().getSupportFragmentManager(), TAG);
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
            fragmentTransaction.commit();
        }
    }
}