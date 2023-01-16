package com.github.lany192.dialog;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import androidx.fragment.app.FragmentActivity;

import com.github.lany192.interfaces.SimpleActivityLifecycleCallbacks;
import com.github.lany192.log.XLog;

import java.lang.ref.SoftReference;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Dialog弹窗队列管理
 */
public class DialogHelper {
    private static DialogHelper instance;
    private final XLog log = XLog.tag(getClass().getSimpleName());
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

    /**
     * 正在显示的单例对话框id
     */
    private final Set<Long> ids = new ArraySet<>();

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

    public void show(@NonNull DialogFragment dialog) {
        dialog.addOnDismissListener(d -> ids.remove(dialog.getDialogId()));
        if (dialog.isSingle()) {
            if (ids.contains(dialog.getDialogId())) {
                log.i("单例对话框，已经显示了，忽略id:" + dialog.getDialogId());
            } else {
                ids.add(dialog.getDialogId());
                dialog.show(currentActivity.get());
            }
        } else {
            dialog.show(currentActivity.get());
        }
    }

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                if (activity instanceof FragmentActivity) {
                    currentActivity = new SoftReference<>((FragmentActivity) activity);
                }
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                if (activity instanceof FragmentActivity) {
                    currentActivity = new SoftReference<>((FragmentActivity) activity);
                }
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                if (currentDialog != null && currentDialog.requireActivity() == activity) {
                    log.i("宿主销毁，主动注销对话框");
                    currentDialog.cancel();
                    currentDialog = null;
                }
            }
        });
    }
}