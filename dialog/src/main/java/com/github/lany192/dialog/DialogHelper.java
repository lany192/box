package com.github.lany192.dialog;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

import java.lang.ref.SoftReference;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Dialog弹窗队列管理
 */
public class DialogHelper {
    private static DialogHelper instance;
    private final Logger.Builder log = XLog.tag(getClass().getSimpleName());
    /**
     * 带优先级弹窗队列
     */
    private final Queue<DialogFragment> queue = new PriorityQueue<>();
    /**
     * 当前正在显示对话框
     */
    private DialogFragment currentDialog;
    /**
     * 当前Activity
     */
    private SoftReference<FragmentActivity> currentActivity;

    private DialogHelper() {
    }

    public static DialogHelper get() {
        if (instance == null) {
            synchronized (DialogHelper.class) {
                if (instance == null) {
                    instance = new DialogHelper();
                }
            }
        }
        return instance;
    }

    public void push2show(@NonNull DialogFragment dialog) {
        queue.offer(dialog);
        show();
        log.i("加入队列,并且显示" + queue.size());
    }

    public void push2queue(@NonNull DialogFragment dialog) {
        queue.offer(dialog);
        log.i("加入队列不展示,暂不显示" + queue.size());
    }

    /**
     * 清空队列
     */
    public void clear() {
        queue.clear();
        if (currentDialog != null) {
            currentDialog.cancel();
            currentDialog = null;
        }
    }

    /**
     * 是否有弹窗正在显示
     */
    public boolean isShowing() {
        return currentDialog != null;
    }

    /**
     * 开始显示对话框
     */
    public void show() {
        if (currentDialog != null) {
            log.i("正在显示对话框");
            return;
        }
        if (!queue.isEmpty()) {
            currentDialog = queue.poll();
            currentDialog.addOnDismissListener(dialog -> {
                if (currentDialog != null) {
                    currentDialog.cancel();
                    currentDialog = null;
                }
                log.i("显示下一个对话框" + queue.size());
                show();
            });
            currentDialog.show(currentActivity.get());
        }
    }

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                if (activity instanceof FragmentActivity) {
                    currentActivity = new SoftReference<>((FragmentActivity) activity);
                }
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                //宿主销毁，主动注销对话框
                if (currentDialog != null && currentDialog.requireActivity() == activity) {
                    currentDialog.cancel();
                    currentDialog = null;
                }
            }
        });
    }
}