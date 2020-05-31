package com.github.lany192.box.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.utils.DensityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * 这个基类要放在这个包中是为了复写父类的show(FragmentManager manager, String tag)
 */
public abstract class DialogFragment extends androidx.fragment.app.DialogFragment {
    final String TAG = this.getClass().getSimpleName();
    View mContentView;
    boolean canceledOnTouchOutside = true;
    Logger.Builder log = XLog.tag(TAG);
    FragmentActivity self;

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void init();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.self = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Subscribe
    public void onEvent(NetWorkEvent event) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        getDialog().setCancelable(isCancelable());
        getDialog().setCanceledOnTouchOutside(canceledOnTouchOutside);
        if (!isCancelable()) {
            getDialog().setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
        }
        mContentView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return mContentView.findViewById(id);
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setLayout(getDialogWidth(), WindowManager.LayoutParams.WRAP_CONTENT);
        }
        init();
    }

    protected int getDialogWidth() {
        return DensityUtils.dp2px(300);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (isAdded()) {
            Log.w(TAG, "已经显示，忽略......");
        } else {
            super.show(manager, tag);
        }
    }

    public void show(FragmentActivity activity) {
        show(activity.getSupportFragmentManager(), TAG);
    }

    public void cancel() {
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().cancel();
        }
    }
}