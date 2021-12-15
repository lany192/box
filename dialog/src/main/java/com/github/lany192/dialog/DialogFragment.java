package com.github.lany192.dialog;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

/**
 * 对话框基类
 */
public abstract class DialogFragment extends androidx.fragment.app.DialogFragment {
    protected final String TAG = this.getClass().getName();
    protected Logger.Builder log = XLog.tag(TAG);
    private boolean flag;

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        if (flag || isAdded() || null != manager.findFragmentByTag(tag)) {
            log.d("已经显示，忽略......");
        } else {
            flag = true;
            super.show(manager, tag);
            new Handler().post(() -> flag = false);
        }
    }

    public void show(@NonNull Context context) {
        FragmentActivity activity = context2activity(context);
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            show(activity.getSupportFragmentManager());
        } else {
            log.e("没有context，不能调起对话框");
        }
    }

    public void show(@NonNull Fragment fragment) {
        show(fragment.requireActivity());
    }

    public void show(FragmentManager manager) {
        show(manager, TAG);
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
