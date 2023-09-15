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

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Dialog弹窗队列管理
 */
public class DialogHelper {
    private volatile static DialogHelper instance;
    private final XLog log = XLog.tag(getClass().getSimpleName());
    /**
     * 带优先级弹窗队列
     */
    private final Queue<PriorityDialog> dialogQueue = new PriorityQueue<>();
    /**
     * 当前正在显示对话框
     */
    private PriorityDialog currentDialog;
    /**
     * 堆栈
     */
    private final Stack<FragmentActivity> activityStack = new Stack<>();

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

    public void push2show(@NonNull PriorityDialog dialog) {
        if (dialog != null) {
            dialogQueue.offer(dialog);
            show();
            log.i("加入队列,并且显示" + dialogQueue.size());
        }
    }

    public void push2queue(@NonNull PriorityDialog dialog) {
        if (dialog != null) {
            dialogQueue.offer(dialog);
            log.i("加入队列不展示,暂不显示" + dialogQueue.size());
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
        if (!dialogQueue.isEmpty()) {
            FragmentActivity activity = getTopActivity();
            if (activity != null) {
                currentDialog = dialogQueue.poll();
                currentDialog.addOnDismissListener(dialog -> {
                    if (currentDialog != null) {
                        currentDialog.cancel();
                        currentDialog = null;
                    }
                    log.i("显示下一个对话框" + dialogQueue.size());
                    show();
                });
                currentDialog.show(activity);
            } else {
                log.e("没有context，不能调起对话框");
            }
        }
    }

    public void show(@NonNull PriorityDialog dialog) {
        FragmentActivity activity = getTopActivity();
        if (activity != null) {
            if (dialog.isSingle() && ids.contains(dialog.getDialogId())) {
                log.i("单例对话框，已经显示了，忽略id:" + dialog.getDialogId());
            } else {
                dialog.addOnDismissListener(d -> ids.remove(dialog.getDialogId()));
                dialog.show(activity);
            }
        } else {
            log.e("没有context，不能调起对话框");
        }
    }

    private FragmentActivity getTopActivity() {
        if(activityStack.isEmpty()){
            return null;
        }
        FragmentActivity activity = activityStack.lastElement();
        if (activity != null) {
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                return activity;
            } else {
                activityStack.remove(activity);
            }
        }
        if (activityStack.size() > 0) {
            return getTopActivity();
        }
        return null;
    }

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                if (activity instanceof FragmentActivity) {
                    activityStack.add((FragmentActivity) activity);
                }
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                if (activity instanceof FragmentActivity) {
                    activityStack.remove((FragmentActivity) activity);
                }
                if (currentDialog != null && currentDialog.requireActivity() == activity) {
                    log.i("宿主销毁，主动注销对话框");
                    currentDialog.cancel();
                    currentDialog = null;
                }
            }
        });
    }

    public void addSingleDialogId(long dialogId) {
        ids.add(dialogId);
    }
}