package com.github.lany192.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.lany192.log.LogUtils;
import com.github.lany192.log.XLog;
import com.github.lany192.utils.ContextUtils;
import com.github.lany192.utils.DensityUtils;
import com.github.lany192.utils.PhoneUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 对话框基类
 */
public abstract class PriorityDialog extends DialogFragment implements Comparable<PriorityDialog> {
    protected final String TAG = this.getClass().getName();
    private final List<DialogInterface.OnDismissListener> dismissListeners = new ArrayList<>();
    protected XLog log = XLog.tag(TAG);
    private boolean canceledOnTouchOutside = true;
    private boolean isInitLoaded;
    private boolean flag;
    /**
     * 优先级，数值越大优先级越高，优先级仅在队列中生效
     */
    private int priority;
    /**
     * 是否单例模式
     */
    private boolean single;

    /**
     * 对话框id
     */
    private long dialogId = this.getClass().hashCode();

    @Override
    public int compareTo(@NonNull PriorityDialog other) {
        //比较优先级
        return other.getPriority() - getPriority();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        for (DialogInterface.OnDismissListener listener : dismissListeners) {
            listener.onDismiss(dialog);
        }
    }

    public long getDialogId() {
        return dialogId;
    }

    /**
     * 是否单例模式
     */
    public boolean isSingle() {
        return single;
    }

    /**
     * 设置单例模式
     * 指定唯一不变的对话框id表示单例模式，多次调用只显示一次
     */
    public void setId(long dialogId) {
        this.dialogId = dialogId;
        this.single = true;
    }

    /**
     * 对话框背景蒙层透明度
     */
    public float getAmount() {
        return 0.5f;
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
            try {
                //super.show(manager, tag);

                Field dismissed = DialogFragment.class.getDeclaredField("mDismissed");
                dismissed.setAccessible(true);
                dismissed.set(this, false);

                Field shown = DialogFragment.class.getDeclaredField("mShownByMe");
                shown.setAccessible(true);
                shown.set(this, true);

                FragmentTransaction ft = manager.beginTransaction();
                ft.add(this, tag);
                ft.commitAllowingStateLoss();
                if (isSingle()) {
                    DialogHelper.get().addSingleDialogId(getDialogId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.e("对话框显示异常：" + e.getMessage());
            }
            new Handler().post(() -> flag = false);
        }
    }

    public void show(@NonNull Context context) {
        FragmentActivity activity = ContextUtils.context2activity(context);
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
        show(manager, TAG + this);
    }

    public void cancel() {
        try {
            dismissNow();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                dismissAllowingStateLoss();
            } catch (Exception e1) {
                e1.printStackTrace();
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.remove(this);
                    transaction.commitAllowingStateLoss();
                } else {
                    log.e("对话框context异常");
                }
            }
        }
    }

    public int getColor(@ColorRes int colorResId) {
        return ContextCompat.getColor(requireContext(), colorResId);
    }

    public String getStringId(@StringRes int resId) {
        return ContextUtils.getContext().getString(resId);
    }

    protected abstract void init();

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

    /**
     * 是否是底部弹窗样式
     */
    protected boolean bottomStyle() {
        return false;
    }

    @Override
    public int getTheme() {
        if (bottomStyle()) {
            return R.style.BottomDialogTheme;
        }
        return super.getTheme();
    }

    protected int getGravity() {
        if (bottomStyle()) {
            return Gravity.BOTTOM;
        } else {
            return Gravity.CENTER;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), this.getTheme()) {
            @Override
            public boolean onTouchEvent(@NonNull MotionEvent event) {
                if (canceledOnTouchOutside) {
                    Window window = getWindow();
                    if (window != null) {
                        View decorView = window.getDecorView();
                        int slop = ViewConfiguration.get(getContext()).getScaledWindowTouchSlop();
                        //是否在对话框外点击
                        if ((event.getX() < -slop)
                                || (event.getY() < -slop)
                                || (event.getX() > (decorView.getWidth() + slop))
                                || (event.getY() > (decorView.getHeight() + slop))) {
                            onTouchDialogOutside();
                        }
                    }
                }
                return super.onTouchEvent(event);
            }

            @Override
            public void onBackPressed() {
                onBackKeyClicked();
                if (isCancelable()) {
                    super.onBackPressed();
                }
            }
        };
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(isCancelable());
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        resetWindow(dialog);
        return dialog;
    }

    public void resetWindow(Dialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setDimAmount(getAmount());
            window.setGravity(getGravity());
            window.setLayout(getDialogWidth(), getDialogHeight());
            if (bottomStyle() && PhoneUtils.hasNavigationBar()) {
                //处理导航栏区域
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    /**
     * 对话框外部点击触发点
     */
    public void onBackKeyClicked() {
        LogUtils.i("返回按键点击");
    }

    /**
     * 对话框外部点击触发点
     */
    public void onTouchDialogOutside() {
        LogUtils.i("对话框外部点击");
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        Dialog dialog = getDialog();
        if (dialog != null) {
            resetWindow(dialog);
        }
        if (!isInitLoaded) {
            isInitLoaded = true;
            init();
        }
    }
}
