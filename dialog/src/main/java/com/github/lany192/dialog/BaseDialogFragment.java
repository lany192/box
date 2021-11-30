package com.github.lany192.dialog;

import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

/**
 * 对话框基类
 */
public abstract class BaseDialogFragment extends DialogFragment {
    protected final String TAG = this.getClass().getName();
    protected Logger.Builder log = XLog.tag(TAG);

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        if (isAdded()) {
            log.w("已经显示，忽略......");
        } else {
            super.show(manager, tag);
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
