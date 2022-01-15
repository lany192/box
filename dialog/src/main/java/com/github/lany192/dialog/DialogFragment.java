package com.github.lany192.dialog;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 对话框基类
 */
public abstract class DialogFragment extends androidx.fragment.app.DialogFragment
        implements Comparable<DialogFragment> {
    protected final String TAG = this.getClass().getName();
    protected Logger.Builder log = XLog.tag(TAG);
    private boolean flag;
    /**
     * 优先级，数值越大优先级越高，优先级仅在队列中生效
     */
    private int priority;
    /**
     * 隐藏监听器
     */
    private final List<DialogInterface.OnDismissListener> dismissListeners = new ArrayList<>();

    @Override
    public int compareTo(@NonNull DialogFragment other) {
        //比较优先级
        return other.getPriority() - getPriority();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        for (DialogInterface.OnDismissListener listener : dismissListeners) {
            listener.onDismiss(dialog);
        }
    }

    /**
     * 获取优先级
     */
    public int getPriority() {
        return priority;
    }

    /**
     * 设置优先级，优先级仅在队列中生效
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * 添加隐藏事件监听
     */
    public void addOnDismissListener(DialogInterface.OnDismissListener listener) {
        if (listener != null) {
            this.dismissListeners.add(listener);
        }
    }

    public void show() {
        DialogHelper.get().show(this);
    }

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

    private void show(FragmentManager manager) {
        show(manager, TAG + toString());
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
