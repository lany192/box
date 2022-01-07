package com.github.lany192.dialog;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 弹窗队列管理
 */
public class DialogQueueHelper {
    private final Logger.Builder log = XLog.tag(getClass().getSimpleName());
    private static DialogQueueHelper instance;
    /**
     * 弹窗队列
     */
    private final Queue<DialogFragment> dialogQueue = new PriorityQueue<>();
    /**
     * 当前正在显示对话框
     */
    private DialogFragment currentDialog;
    /**
     * 当前能用于显示对话框的Activity
     */
    private FragmentActivity currentActivity;

    private DialogQueueHelper() {
    }

    public static DialogQueueHelper get() {
        if (instance == null) {
            synchronized (DialogQueueHelper.class) {
                if (instance == null) {
                    instance = new DialogQueueHelper();
                }
            }
        }
        return instance;
    }

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                if (activity instanceof FragmentActivity) {
                    currentActivity = (FragmentActivity) activity;
                }
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

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

    /**
     * 加入队列
     */
    public void addShow(DialogFragment dialog) {
        if (dialog != null) {
            dialogQueue.offer(dialog);
            show();
        } else {
            log.e("空对象，忽略");
        }
    }

    /**
     * 加入队列，但不马上展示
     */
    public void add(DialogFragment dialog) {
        if (dialog != null) {
            dialogQueue.offer(dialog);
        } else {
            log.e("空对象，忽略");
        }
    }

    /**
     * 清空队列
     */
    public void clear() {
        dialogQueue.clear();
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
        if (dialogQueue.isEmpty()) {
            log.i("空队列，无对话框可显示");
            return;
        }
        if (currentActivity == null) {
            log.i("没有可用的上下文");
            return;
        }
        //出队列
        currentDialog = dialogQueue.poll();
        currentDialog.addOnDismissListener(dialog -> {
            if (currentDialog != null) {
                currentDialog.cancel();
                currentDialog = null;
            }
            show();
        });
        currentDialog.show(currentActivity);
    }
}